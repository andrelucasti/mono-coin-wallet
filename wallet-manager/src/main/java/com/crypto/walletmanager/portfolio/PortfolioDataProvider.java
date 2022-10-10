package com.crypto.walletmanager.portfolio;

import java.util.List;

public interface PortfolioDataProvider {
    PortfolioEntity save(PortfolioEntity portfolio);
    List<PortfolioEntity> findAll();
}
