package com.crypto.wallettransaction.dataprovider.portfolio;

import com.crypto.wallettransaction.business.portfolio.Portfolio;
import com.crypto.wallettransaction.business.portfolio.PortfolioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class PortfolioRepositoryImpl implements PortfolioRepository {

    private final PortfolioDataProviderRepository portfolioDataProviderRepository;

    public PortfolioRepositoryImpl(@Qualifier("portfolioDataProviderRepositoryPhysicalDatabase") PortfolioDataProviderRepository portfolioDataProviderRepository) {
        this.portfolioDataProviderRepository = portfolioDataProviderRepository;
    }

    @Override
    public void save(Portfolio portfolio) {
        portfolioDataProviderRepository.save(new PortfolioEntity(portfolio.id(), portfolio.name()));
    }

    @Override
    public List<Portfolio> findAll() {
        return StreamSupport.stream(portfolioDataProviderRepository.findAll().spliterator(), false)
                .map(portfolioEntity -> new Portfolio(portfolioEntity.getId(), portfolioEntity.getName()))
                .toList();

    }

    @Override
    public Optional<Portfolio> findById(UUID id) {
        return portfolioDataProviderRepository.findById(id)
                .map(portfolioEntity -> new Portfolio(portfolioEntity.getId(), portfolioEntity.getName()));
    }
}