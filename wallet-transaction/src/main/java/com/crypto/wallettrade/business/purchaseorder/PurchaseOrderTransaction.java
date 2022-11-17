package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.wallettrade.business.coin.Coin;

public record PurchaseOrderTransaction(PurchaseOrder purchaseOrder, Coin coin) {
}
