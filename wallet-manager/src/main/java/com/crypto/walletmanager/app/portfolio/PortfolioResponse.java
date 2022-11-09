package com.crypto.walletmanager.app.portfolio;

import java.util.UUID;

public record PortfolioResponse(UUID id, String name, UUID userId) {
}
