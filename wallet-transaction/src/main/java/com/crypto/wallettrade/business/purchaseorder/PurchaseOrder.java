package com.crypto.wallettrade.business.purchaseorder;

import com.google.common.base.Preconditions;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public record PurchaseOrder(UUID portfolioId,
                            String coinSymbol,
                            double quantity,
                            double fee,
                            ZonedDateTime purchaseOrderDate) {
    public PurchaseOrder {
        Objects.requireNonNull(portfolioId, "portfolioId is required");
        Objects.requireNonNull(coinSymbol, "coinSymbol is required");
        Objects.requireNonNull(purchaseOrderDate, "purchaseOrderDate is required");

        Preconditions.checkArgument(quantity >= 0, "quantity can't be less than or equal to zero");
    }
}
