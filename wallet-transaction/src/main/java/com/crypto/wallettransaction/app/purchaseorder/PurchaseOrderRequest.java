package com.crypto.wallettransaction.app.purchaseorder;

import java.time.ZonedDateTime;
import java.util.UUID;

public record PurchaseOrderRequest(UUID portfolioId,
                                   String coinSymbol,
                                   double quantity,
                                   double fee,
                                   ZonedDateTime purchaseOrderDate) {

}