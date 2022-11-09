package com.crypto.walletmanager.dataprovider.portfolio;

import java.util.UUID;

public record PortfolioEntity(UUID id, String name, UUID userId) {

    public PortfolioEntity(String name, UUID userId){
        this(UUID.randomUUID(), name, userId);
    }
}
