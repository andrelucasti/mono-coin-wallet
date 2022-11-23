package com.crypto.wallettransaction.thirdparty.coinMarketCap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CoinData(@JsonProperty("id") Long id,
                       @JsonProperty("name") String name,
                       @JsonProperty("symbol") String symbol,
                       @JsonProperty("quote")Map<String, PriceQuote> quote) {
}
