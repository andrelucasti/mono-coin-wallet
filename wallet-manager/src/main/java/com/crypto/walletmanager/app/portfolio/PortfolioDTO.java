package com.crypto.walletmanager.app.portfolio;

import java.util.Objects;
import java.util.UUID;

public record PortfolioDTO(UUID id, String name, UUID userId) {

    public PortfolioDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(userId);
    }
}
