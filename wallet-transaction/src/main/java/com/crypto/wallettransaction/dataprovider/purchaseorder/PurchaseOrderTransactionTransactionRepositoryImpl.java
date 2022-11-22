package com.crypto.wallettransaction.dataprovider.purchaseorder;

import com.crypto.wallettransaction.business.coin.Coin;
import com.crypto.wallettransaction.business.coin.CurrencyType;
import com.crypto.wallettransaction.business.purchaseorder.PurchaseOrder;
import com.crypto.wallettransaction.business.purchaseorder.PurchaseOrderTransactionRepository;
import com.crypto.wallettransaction.business.purchaseorder.PurchaseOrderTransaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class PurchaseOrderTransactionTransactionRepositoryImpl implements PurchaseOrderTransactionRepository {

    private final PurchaseOrderTransactionEntityDataProvider purchaseOrderTransactionEntityDataProvider;

    public PurchaseOrderTransactionTransactionRepositoryImpl(@Qualifier("purchaseOrderTransactionEntityPhysicalDataProviderProvider") PurchaseOrderTransactionEntityDataProvider purchaseOrderTransactionEntityDataProvider) {
        this.purchaseOrderTransactionEntityDataProvider = purchaseOrderTransactionEntityDataProvider;
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

        purchaseOrderTransactionEntityDataProvider.save(purchaseOrderTransactionEntity);
    }

    @Override
    public List<PurchaseOrderTransaction> findAll() {
        return purchaseOrderTransactionEntityDataProvider.findAll()
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
