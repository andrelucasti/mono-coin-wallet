package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PortfolioEntityRepository extends CrudRepository<PortfolioEntity, UUID> {

    Iterable<PortfolioEntity> findByUserId(UUID userId);
}