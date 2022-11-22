package com.crypto.wallettransaction.dataprovider.purchaseorder;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "PURCHASE_ORDER_TRANSACTION")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
@Getter
public class PurchaseOrderTransactionEntity {
    public PurchaseOrderTransactionEntity() {}

    @Id
    private UUID id;
    @Column(name = "PORTFOLIO_ID", nullable = false)
    private UUID portfolioId;
    @Column(name = "COIN_SYMBOL", nullable = false)
    private String coinSymbol;
    @Column(name = "QUANTITY")
    private double quantity;
    @Column(name = "FEE")
    private double fee;
    @Column(name = "COIN_ATTRIBUTES", nullable = false)
    @Type(type = "json")
    private Map<String, String> coinAttributes;
    @Column(name = "ORDER_DATE", nullable = false)
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
}
