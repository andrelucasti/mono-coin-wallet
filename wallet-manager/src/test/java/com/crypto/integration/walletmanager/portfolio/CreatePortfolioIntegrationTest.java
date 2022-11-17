package com.crypto.integration.walletmanager.portfolio;

import com.crypto.walletmanager.business.portfolio.CreatePortfolio;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioConverter;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDAOImp;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDataProviderInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CreatePortfolioIntegrationTest {

    @Test
    void shouldSaveAndReturnPortfolio() {
        final var portFolioName = "Token Wallet";
        final var userId = UUID.randomUUID();
        final var portfolio = new Portfolio(portFolioName, userId);
        final var portfolioDataProviderInMemory = new PortfolioDataProviderInMemory();

        final var subject = new CreatePortfolio(new PortfolioDAOImp(portfolioDataProviderInMemory, new PortfolioConverter()));
        subject.execute(portfolio);

        final var portfolioList = portfolioDataProviderInMemory.findAll();
        Assertions.assertFalse(portfolioList.isEmpty());

        var portfolioEntity = portfolioList.stream().findAny().get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(portFolioName, portfolioEntity.getName()),
                () -> Assertions.assertEquals(userId, portfolioEntity.getUserId())
        );
    }
}