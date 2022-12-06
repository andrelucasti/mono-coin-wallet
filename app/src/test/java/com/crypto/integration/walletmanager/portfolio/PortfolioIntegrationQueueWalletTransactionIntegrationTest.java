package com.crypto.integration.walletmanager.portfolio;

import com.crypto.integration.walletmanager.AbstractWalletManagerIntegrationTest;
import com.crypto.walletmanager.app.portfolio.PortfolioIntegrationQueue;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Disabled
class PortfolioIntegrationQueueWalletTransactionIntegrationTest extends AbstractWalletManagerIntegrationTest {
    private static final String QUEUE_NAME = "wallet-manager-portfolio-to-wallet-transaction";

    @Autowired
    private PortfolioIntegrationQueue portfolioIntegrationQueue;

    @Test
    void shouldSendToQueuePortfolioAndReturnPortfolio() {
        var id = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var portfolio = new Portfolio("NTF", userId, id);

        portfolioIntegrationQueue.send(portfolio);

        Awaitility.await().atMost(60, TimeUnit.SECONDS).untilAsserted(() ->{
            var receiveMessageResponse = receiveMessage(QUEUE_NAME);
            Assertions.assertThat(receiveMessageResponse.hasMessages()).isTrue();
        });
    }
}