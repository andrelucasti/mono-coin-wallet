package com.crypto.walletmanager.portfolio;

import java.util.List;

public interface PortfolioDAO {
    List<Portfolio> findAll();
    Portfolio save(Portfolio portfolio);
}
