package com.crypto.wallettransaction.thirdparty.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record PriceQuote(@JsonProperty("price") Double price,
                         @JsonProperty("id" ) ZonedDateTime lastUpdateTime) {
}
