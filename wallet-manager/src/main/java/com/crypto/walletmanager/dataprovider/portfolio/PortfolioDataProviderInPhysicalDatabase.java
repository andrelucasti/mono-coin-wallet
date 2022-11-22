package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class PortfolioDataProviderInPhysicalDatabase implements PortfolioDataProvider {

    private final PortfolioDAOEntity portfolioDAOEntity;

    public PortfolioDataProviderInPhysicalDatabase(PortfolioDAOEntity portfolioDAOEntity) {
        this.portfolioDAOEntity = portfolioDAOEntity;
    }

    @Override
    public void save(PortfolioEntity portfolioEntity) {
        portfolioDAOEntity.save(portfolioEntity);

    }

    @Override
    public List<PortfolioEntity> findAll() {

        return StreamSupport
                .stream(portfolioDAOEntity
                        .findAll()
                        .spliterator(), false)
                .toList();
    }

    @Override
    public List<PortfolioEntity> findBy(UUID userId) {
        return StreamSupport
                .stream(portfolioDAOEntity
                        .findByUserId(userId)
                        .spliterator(), false)
                .toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return portfolioDAOEntity.findById(id);
    }
}
