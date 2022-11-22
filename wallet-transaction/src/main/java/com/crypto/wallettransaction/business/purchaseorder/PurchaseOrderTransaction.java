package com.crypto.wallettransaction.business.purchaseorder;

import com.crypto.wallettransaction.business.coin.Coin;

public record PurchaseOrderTransaction(PurchaseOrder purchaseOrder, Coin coin) {
}
