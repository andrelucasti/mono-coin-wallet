package com.crypto.integration.walletmanager.portfolio;



import com.crypto.integration.walletmanager.AbstractWalletManagerIntegrationTest;
import com.crypto.walletmanager.app.portfolio.PortfolioIntegrationTopic;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.net.URISyntaxException;
import java.util.UUID;

@PropertySource(value = "classpath:application-wallet-manager.properties")
class PortfolioIntegrationSendTopicIntegrationTest extends AbstractWalletManagerIntegrationTest {

    private @Value("${cloud.aws.sns.from.wallet-manager.portfolio.topic}") String topicName;

    @Autowired
    private PortfolioIntegrationTopic portfolioIntegrationTopic;

    @SneakyThrows
    @Test
    void shouldSendToTopic() {
        var queueName = "wallet-test";
        var subscribeRequest = createSubscribe(topicName, queueName);

        var userId = UUID.randomUUID();
        var id = UUID.randomUUID();
        var portfolio = new Portfolio("CryptoWallet", userId, id);

        portfolioIntegrationTopic.send(portfolio);

        var receiveMessageResponse = receiveMessage(subscribeRequest.endpoint());

        Assertions.assertThat(receiveMessageResponse.hasMessages()).isTrue();
    }
    
    private SubscribeRequest createSubscribe(String topicName, String queueName) throws URISyntaxException {
        var createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();
        var snsClient = snsClient();
        var createTopicResponse = snsClient.createTopic(createTopicRequest);

        var createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();
        var queueUrl = sqsClient().createQueue(createQueueRequest)
                .queueUrl();

        var subscribeRequest = SubscribeRequest.builder()
                .protocol("sqs")
                .topicArn(createTopicResponse.topicArn())
                .endpoint(queueUrl)
                .build();

        snsClient.subscribe(subscribeRequest);

        return subscribeRequest;
    }
}