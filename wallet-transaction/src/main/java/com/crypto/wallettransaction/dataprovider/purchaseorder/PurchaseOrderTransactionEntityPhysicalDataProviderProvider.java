package com.crypto.wallettransaction.dataprovider.purchaseorder;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
@Transactional(transactionManager = "walletTransaction")
public class PurchaseOrderTransactionEntityPhysicalDataProviderProvider implements PurchaseOrderTransactionEntityDataProvider {

    private final PurchaseOrderDaoEntity purchaseOrderDaoEntity;

    public PurchaseOrderTransactionEntityPhysicalDataProviderProvider(PurchaseOrderDaoEntity purchaseOrderDaoEntity) {
        this.purchaseOrderDaoEntity = purchaseOrderDaoEntity;
    }

    @Override
    public void save(PurchaseOrderTransactionEntity purchaseOrderTransactionEntity) {
        purchaseOrderDaoEntity.save(purchaseOrderTransactionEntity);
    }

    @Override
    public List<PurchaseOrderTransactionEntity> findAll() {
        return StreamSupport.stream(purchaseOrderDaoEntity.findAll().spliterator(), false).toList();
    }
}
