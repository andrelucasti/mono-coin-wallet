package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WalletManagerPortfolioEntityDAO extends CrudRepository<PortfolioEntity, UUID> {

    PortfolioEntity findByUserIdAndName(UUID userId, String name);
    Iterable<PortfolioEntity> findByUserId(UUID userId);
}