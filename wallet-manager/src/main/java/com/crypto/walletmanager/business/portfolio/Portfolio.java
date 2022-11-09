package com.crypto.walletmanager.business.portfolio;

import java.util.Objects;
import java.util.UUID;

public record Portfolio(String name, UUID userId, UUID id) {
    public Portfolio(String name, UUID userId){
        this(name, userId, null);
    }
    public Portfolio {
        Objects.requireNonNull(name);
        Objects.requireNonNull(userId);
    }
}
