package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.wallettrade.business.coin.Coin;

import java.time.ZonedDateTime;
import java.util.UUID;

public record PurchaseOrder(UUID portfolioId,
                            String coinSymbol,
                            double quantity,
                            double fee,
                            ZonedDateTime purchaseOrderDate,
                            Coin coin) {
}
