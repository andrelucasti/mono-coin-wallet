package com.crypto.integration.walletmanager.portfolio;



import com.crypto.integration.walletmanager.AbstractWalletManagerIntegrationTest;
import com.crypto.walletmanager.app.portfolio.PortfolioIntegrationTopic;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
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
        var portfolio = new Portfolio("CryptoWallet", userId, id);

        portfolioIntegrationTopic.send(portfolio);

        Awaitility.await().atMost(60, TimeUnit.SECONDS).untilAsserted(() ->{
            var receiveMessageResponse = receiveMessage(subscribeRequest.endpoint());
            Assertions.assertThat(receiveMessageResponse.hasMessages()).isTrue();
        });
    }
    
    private SubscribeRequest createSubscribe(String topicName, String queueName) throws URISyntaxException {
        var topicArn = createTopic(topicName);
        var queueUrl = createQueue(queueName);

        var subscribeRequest = SubscribeRequest.builder()
                .protocol("sqs")
                .topicArn(topicArn)
                .endpoint(queueUrl)
                .build();

        snsClient().subscribe(subscribeRequest);

        return subscribeRequest;
    }

    private String createQueue(String queueName) throws URISyntaxException {
        var createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();
        return sqsClient().createQueue(createQueueRequest).queueUrl();
    }

    private String createTopic(String topicName) throws URISyntaxException {
        var createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();
        return snsClient().createTopic(createTopicRequest).topicArn();
    }
}