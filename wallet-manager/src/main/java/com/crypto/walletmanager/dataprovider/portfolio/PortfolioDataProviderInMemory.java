package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PortfolioDataProviderInMemory implements PortfolioDataProvider {

    private  final Map<UUID, PortfolioEntity> memory;
    public PortfolioDataProviderInMemory() {
        this.memory  = new HashMap<>();
    }

    @Override
    public List<PortfolioEntity> findAll() {
        return this.memory.values().stream().toList();
    }

    @Override
    public List<PortfolioEntity> findBy(UUID userId) {
       return this.memory.values().stream()
                    .filter(portfolioEntity -> portfolioEntity.getUserId().equals(userId))
                    .toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return Optional.ofNullable(this.memory.get(id));
    }

    @Override
    public void save(PortfolioEntity portfolio) {
        this.memory.put(portfolio.getId(), portfolio);
    }
}
