package com.crypto.walletmanager.dataprovider.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PortfolioDAOImp implements PortfolioDAO {

    private final PortfolioDataProvider portfolioDataProvider;
    private final PortfolioConverter portfolioConverter;

    public PortfolioDAOImp(@Qualifier("portfolioDataProviderInPhysicalDatabase") PortfolioDataProvider portfolioDataProvider,
                           PortfolioConverter portfolioConverter) {
        this.portfolioDataProvider = portfolioDataProvider;
        this.portfolioConverter = portfolioConverter;
    }

    @Override
    public List<Portfolio> findAll() {
        List<PortfolioEntity> portfolioEntityList = portfolioDataProvider.findAll();

        return portfolioEntityList
                    .stream()
                    .map(portfolioConverter::fromModelToEntity)
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
                .map(portfolioConverter::fromModelToEntity)
                .toList();
    }

    @Override
    public Optional<Portfolio> findById(UUID id) {
        return portfolioDataProvider.findById(id)
                .map(portfolioConverter::fromModelToEntity);
    }
}
