package com.crypto.integration.walletmanager.portfolio;

import com.crypto.walletmanager.portfolio.CreatePortfolio;
import com.crypto.walletmanager.portfolio.Portfolio;
import com.crypto.walletmanager.portfolio.PortfolioInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CreatePortfolioIntegrationTest {

    @Test
    void shouldSaveAndReturnPortfolio() {
        final var portFolioName = "Token Wallet";
        final var userId = UUID.randomUUID();
        final var portfolio = new Portfolio(portFolioName, userId);
        final var portfolioDAO = new PortfolioInMemory();

        final var subject = new CreatePortfolio(portfolioDAO);
        subject.execute(portfolio);

        final var portfolioList = portfolioDAO.findAll();
        Assertions.assertFalse(portfolioList.isEmpty());

        Portfolio portfolioReturned = portfolioList.stream().findAny().get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(portFolioName, portfolioReturned.name()),
                () -> Assertions.assertEquals(userId, portfolioReturned.userId())
        );
    }
}