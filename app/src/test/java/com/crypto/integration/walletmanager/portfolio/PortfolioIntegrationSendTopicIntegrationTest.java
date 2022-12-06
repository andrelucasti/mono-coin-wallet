package com.crypto.integration.walletmanager.portfolio;



import com.crypto.integration.walletmanager.AbstractWalletManagerIntegrationTest;
import com.crypto.walletmanager.app.portfolio.PortfolioDTO;
import com.crypto.walletmanager.app.portfolio.PortfolioIntegrationTopic;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@PropertySource(value = "classpath:application-wallet-manager.properties")
class PortfolioIntegrationSendTopicIntegrationTest extends AbstractWalletManagerIntegrationTest {

    private @Value("${cloud.aws.sns.from.wallet-manager.portfolio.topic}") String topicName;

    @Autowired
    private PortfolioIntegrationTopic portfolioIntegrationTopic;

    @Test
    void shouldSendToTopic() throws URISyntaxException {
        var queueName = "wallet-test";
        var subscribeRequest = createSubscribe(topicName, queueName);

        var userId = UUID.randomUUID();
        var id = UUID.randomUUID();
        var name = "CryptoWallet";
        var portfolio = new Portfolio(name, userId, id);

        portfolioIntegrationTopic.send(portfolio);

        Awaitility.await().atMost(60, TimeUnit.SECONDS).untilAsserted(() ->{
            var receiveMessageResponse = receiveMessage(subscribeRequest.endpoint());
            Assertions.assertThat(receiveMessageResponse.hasMessages()).isTrue();

        });

        Awaitility.await().atMost(60, TimeUnit.SECONDS).untilAsserted(()->{
            var receiveMessageResponse = receiveMessage(subscribeRequest.endpoint());
            Optional<PortfolioDTO> optionalPortfolioDTO = receiveMessageResponse.messages().stream().findAny()
                    .map(Message::body)
                    .map(message -> convertFromSNSPayloadToObject(message, PortfolioDTO.class));

            Assertions.assertThat(optionalPortfolioDTO).isNotEmpty();
            PortfolioDTO portfolioDTO = optionalPortfolioDTO.get();

            Assertions.assertThat(portfolioDTO.id()).isEqualTo(id);
            Assertions.assertThat(portfolioDTO.name()).isEqualTo(name);
            Assertions.assertThat(portfolioDTO.userId()).isEqualTo(userId);
        });
    }
}