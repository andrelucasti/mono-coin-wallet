package com.crypto.wallettrade.dataprovider.purchaseorder;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public class PurchaseOrderTransactionEntity {

    private UUID id;
    private UUID portfolioId;
    private String coinSymbol;
    private double quantity;
    private double fee;
    private Map<String, String> coinAttributes;
    private ZonedDateTime purchaseOrderDate;

    public PurchaseOrderTransactionEntity(UUID portfolioId,
                                          String coinSymbol,
                                          double quantity,
                                          double fee,
                                          Map<String, String> coinAttributes,
                                          ZonedDateTime purchaseOrderDate) {
        this.id = UUID.randomUUID();
        this.portfolioId = portfolioId;
        this.coinSymbol = coinSymbol;
        this.quantity = quantity;
        this.fee = fee;
        this.coinAttributes = coinAttributes;
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getFee() {
        return fee;
    }

    public Map<String, String> getCoinAttributes() {
        return coinAttributes;
    }

    public ZonedDateTime getPurchaseOrderDate() {
        return purchaseOrderDate;
    }
}
