package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PortfolioDataProviderInMemory implements PortfolioDataProvider {

    private  final List<PortfolioEntity> memory;
    public PortfolioDataProviderInMemory() {
        this.memory  = new ArrayList<>();
    }

    @Override
    public List<PortfolioEntity> findAll() {
        return this.memory;
    }

    @Override
    public List<PortfolioEntity> findBy(UUID userId) {
       return this.memory.stream()
                    .filter(portfolioEntity -> portfolioEntity.userId().equals(userId))
                    .toList();
    }

    @Override
    public void save(PortfolioEntity portfolio) {
        this.memory.add(portfolio);
    }
}
