package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class PortfolioDataProviderInPhysicalDatabase implements PortfolioDataProvider {

    private final PortfolioEntityRepository portfolioEntityRepository;

    public PortfolioDataProviderInPhysicalDatabase(PortfolioEntityRepository portfolioEntityRepository) {
        this.portfolioEntityRepository = portfolioEntityRepository;
    }

    @Override
    public void save(PortfolioEntity portfolioEntity) {
        portfolioEntityRepository.save(portfolioEntity);

    }

    @Override
    public List<PortfolioEntity> findAll() {

        return StreamSupport
                .stream(portfolioEntityRepository
                        .findAll()
                        .spliterator(), false)
                .toList();
    }

    @Override
    public List<PortfolioEntity> findBy(UUID userId) {
        return StreamSupport
                .stream(portfolioEntityRepository
                        .findByUserId(userId)
                        .spliterator(), false)
                .toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return portfolioEntityRepository.findById(id);
    }
}
