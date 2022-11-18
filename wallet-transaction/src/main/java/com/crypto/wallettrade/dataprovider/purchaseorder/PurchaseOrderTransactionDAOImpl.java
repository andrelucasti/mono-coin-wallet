package com.crypto.wallettrade.dataprovider.purchaseorder;

import com.crypto.wallettrade.business.coin.Coin;
import com.crypto.wallettrade.business.coin.CurrencyType;
import com.crypto.wallettrade.business.purchaseorder.PurchaseOrder;
import com.crypto.wallettrade.business.purchaseorder.PurchaseOrderTransaction;
import com.crypto.wallettrade.dataprovider.DAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class PurchaseOrderTransactionDAOImpl implements DAO<PurchaseOrderTransaction> {

    private final PurchaseOrderTransactionEntityData purchaseOrderTransactionEntityData;

    public PurchaseOrderTransactionDAOImpl(@Qualifier("purchaseOrderTransactionEntityPhysicalDatabase") PurchaseOrderTransactionEntityData purchaseOrderTransactionEntityData) {
        this.purchaseOrderTransactionEntityData = purchaseOrderTransactionEntityData;
    }

    @Override
    public void save(PurchaseOrderTransaction purchaseOrderTransaction) {

        var coinAttribute = Map.of(
                    "name", purchaseOrderTransaction.coin().name(),
                    "symbol", purchaseOrderTransaction.coin().symbol(),
                    "price", String.valueOf(purchaseOrderTransaction.coin().currentValue()),
                    "currencyType", purchaseOrderTransaction.coin().currencyType().name());

        var purchaseOrderTransactionEntity = new PurchaseOrderTransactionEntity(
                purchaseOrderTransaction.purchaseOrder().portfolioId(),
                purchaseOrderTransaction.purchaseOrder().coinSymbol(),
                purchaseOrderTransaction.purchaseOrder().quantity(),
                purchaseOrderTransaction.purchaseOrder().fee(),
                coinAttribute,
                purchaseOrderTransaction.purchaseOrder().purchaseOrderDate());

        purchaseOrderTransactionEntityData.save(purchaseOrderTransactionEntity);
    }

    @Override
    public List<PurchaseOrderTransaction> findAll() {
        return purchaseOrderTransactionEntityData.findAll()
                .stream()
                .map(purchaseOrderTransactionEntity ->
                        new PurchaseOrderTransaction(
                            new PurchaseOrder(purchaseOrderTransactionEntity.getPortfolioId(),
                                    purchaseOrderTransactionEntity.getCoinSymbol(),
                                    purchaseOrderTransactionEntity.getQuantity(),
                                    purchaseOrderTransactionEntity.getFee(),
                                    purchaseOrderTransactionEntity.getPurchaseOrderDate()),
                            new Coin(
                                    purchaseOrderTransactionEntity.getCoinAttributes().get("symbol"),
                                    purchaseOrderTransactionEntity.getCoinAttributes().get("name"),
                                    Double.parseDouble(purchaseOrderTransactionEntity.getCoinAttributes().get("price")),
                                    CurrencyType.valueOf(purchaseOrderTransactionEntity.getCoinAttributes().get("currencyType"))
                ))).toList();
    }
}
