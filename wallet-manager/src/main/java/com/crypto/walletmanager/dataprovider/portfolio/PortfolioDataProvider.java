package com.crypto.walletmanager.dataprovider.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioDataProvider {
    void save(PortfolioEntity portfolio);
    List<PortfolioEntity> findAll();
    List<PortfolioEntity> findBy(UUID userId);

    Optional<PortfolioEntity> findById(UUID id);
}
