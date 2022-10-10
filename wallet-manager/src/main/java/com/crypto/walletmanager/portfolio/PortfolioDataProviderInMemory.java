package com.crypto.walletmanager.portfolio;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PortfolioDataProviderInMemory implements PortfolioDataProvider {

    private  final List<PortfolioEntity> memory;
    public PortfolioDataProviderInMemory() {
        this.memory  = new ArrayList<>();;
    }

    @Override
    public List<PortfolioEntity> findAll() {
        return this.memory;
    }

    @Override
    public PortfolioEntity save(PortfolioEntity portfolio) {
        this.memory.add(portfolio);
        return new PortfolioEntity(portfolio.name(), portfolio.userId());
    }
}
