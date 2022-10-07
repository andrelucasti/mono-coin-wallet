package com.crypto.walletmanager.portfolio;

import java.util.List;

public interface PortfolioDAO {
    List<Portfolio> findAll();
    void save(Portfolio portfolio);
}
