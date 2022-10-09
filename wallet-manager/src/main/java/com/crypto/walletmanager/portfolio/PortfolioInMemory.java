package com.crypto.walletmanager.portfolio;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public void save(Portfolio portfolio) {
        this.memory.add(portfolio);
    }
}
