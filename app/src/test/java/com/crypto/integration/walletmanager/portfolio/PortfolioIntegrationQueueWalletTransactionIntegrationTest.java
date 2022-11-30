package com.crypto.integration.walletmanager.portfolio;

import com.crypto.integration.walletmanager.AbstractWalletManagerIntegrationTest;
import com.crypto.integration.wallettransaction.AbstractWalletTransactionIntegrationTest;
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

class PortfolioIntegrationQueueWalletTransactionIntegrationTest extends AbstractWalletManagerIntegrationTest {
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
}