package com.crypto.walletmanager.portfolio;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PortfolioInMemory implements PortfolioDAO {

    private  final List<Portfolio> memory;

    public PortfolioInMemory() {
        this.memory  = new ArrayList<>();;
    }

    @Override
    public List<Portfolio> findAll() {
        return this.memory;
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        this.memory.add(portfolio);
        return new Portfolio(portfolio.name(), portfolio.userId(), UUID.randomUUID());
    }
}
