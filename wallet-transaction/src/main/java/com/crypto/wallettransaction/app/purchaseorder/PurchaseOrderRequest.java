package com.crypto.wallettransaction.app.purchaseorder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public record PurchaseOrderRequest(@JsonProperty(value = "portfolioId", required = true) UUID portfolioId,
                                   @JsonProperty(value = "coinSymbol", required = true) String coinSymbol,
                                   @JsonProperty(value = "quantity", required = true) double quantity,
                                   @JsonProperty(value = "fee", required = true) double fee,
                                   @JsonProperty(value = "purchaseOrderDate", required = true) ZonedDateTime purchaseOrderDate) {

}