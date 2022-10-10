package com.crypto.walletmanager.portfolio;

import java.util.UUID;

public record PortfolioResponse(UUID id, String name, UUID userId) {
}
