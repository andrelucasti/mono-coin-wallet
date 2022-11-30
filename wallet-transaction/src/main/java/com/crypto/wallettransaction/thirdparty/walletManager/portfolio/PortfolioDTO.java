package com.crypto.wallettransaction.thirdparty.walletManager.portfolio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PortfolioDTO(@JsonProperty("id") UUID id,
                           @JsonProperty("name") String name) {
}
