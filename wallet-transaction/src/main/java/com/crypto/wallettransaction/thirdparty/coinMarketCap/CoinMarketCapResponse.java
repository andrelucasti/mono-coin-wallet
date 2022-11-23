package com.crypto.wallettransaction.thirdparty.coinMarketCap;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CoinMarketCapResponse(@JsonProperty(value = "status", required = true) Status status,
                                    @JsonProperty(value = "data", required = true) Map<String, CoinData> coinData)   {

}
