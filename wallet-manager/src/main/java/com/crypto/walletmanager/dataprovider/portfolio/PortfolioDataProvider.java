package com.crypto.walletmanager.dataprovider.portfolio;

import java.util.List;
import java.util.UUID;

public interface PortfolioDataProvider {
    void save(PortfolioEntity portfolio);
    List<PortfolioEntity> findAll();
    List<PortfolioEntity> findBy(UUID userId);
}
