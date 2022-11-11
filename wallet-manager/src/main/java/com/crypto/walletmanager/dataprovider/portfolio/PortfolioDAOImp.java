package com.crypto.walletmanager.dataprovider.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PortfolioDAOImp implements PortfolioDAO {

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
    public void save(Portfolio portfolio) {
        portfolioDataProvider.save(new PortfolioEntity(portfolio.name(), portfolio.userId()));
    }

    @Override
    public List<Portfolio> findBy(UUID userId) {
        return portfolioDataProvider.findBy(userId)
            .stream()
            .map(portfolioEntity -> new Portfolio(portfolioEntity.name(), portfolioEntity.userId(), portfolioEntity.id()))
            .toList();
    }

    @Override
    public Optional<Portfolio> findById(UUID id) {
        return portfolioDataProvider.findById(id)
                .map(portfolioEntity -> new Portfolio(portfolioEntity.name(), portfolioEntity.userId()));
    }
}
