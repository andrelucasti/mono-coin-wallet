package com.crypto.walletmanager.portfolio;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortfolioDAOImp implements PortfolioDAO{

    private final PortfolioDataProvider portfolioDataProvider;

    public PortfolioDAOImp(PortfolioDataProvider portfolioDataProvider) {
        this.portfolioDataProvider = portfolioDataProvider;
    }

    @Override
    public List<Portfolio> findAll() {
        List<PortfolioEntity> portfolioEntityList = portfolioDataProvider.findAll();

        return portfolioEntityList
                    .stream()
                .map(portfolioEntity -> new Portfolio(portfolioEntity.name(), portfolioEntity.userId(), portfolioEntity.id()))
                .toList();
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        var portfolioEntity = portfolioDataProvider.save(new PortfolioEntity(portfolio.name(), portfolio.userId()));

        return new Portfolio(portfolioEntity.name(), portfolioEntity.userId(), portfolioEntity.id());
    }
}
