package com.crypto.integration.walletmanager.portfolio;

import com.crypto.integration.AbstractIntegrationTest;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.app.portfolio.PortfolioIntegrationQueue;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

class PortfolioIntegrationQueueIntegrationTest extends AbstractIntegrationTest {
    private static final String QUEUE_NAME = "wallet-manager-portfolio-to-wallet-transaction";

    @Autowired
    private PortfolioIntegrationQueue portfolioIntegrationQueue;

    @SneakyThrows
    @Test
    void shouldSendToQueuePortfolioAndReturnPortfolio() {
        var userId = UUID.randomUUID();
        var portfolio = new Portfolio("NTF", userId);

        portfolioIntegrationQueue.send(portfolio);

        var receiveMessageResponse = receiveMessage(QUEUE_NAME);
        Assertions.assertThat(receiveMessageResponse.hasMessages()).isTrue();
    }

    protected AwsCredentialsProvider getAwsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    protected SqsClient sqsClient() throws URISyntaxException {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(new URI("http://localhost:4566"))
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
    }

    protected ReceiveMessageResponse receiveMessage(String queueUrl) throws URISyntaxException {
        var sqsClient = sqsClient();
        var receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        return sqsClient.receiveMessage(receiveMessageRequest);
    }
}