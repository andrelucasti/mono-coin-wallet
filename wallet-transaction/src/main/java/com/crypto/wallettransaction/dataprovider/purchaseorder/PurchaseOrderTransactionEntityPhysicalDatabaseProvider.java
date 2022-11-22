package com.crypto.wallettransaction.dataprovider.purchaseorder;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public class PurchaseOrderTransactionEntityPhysicalDatabaseProvider implements PurchaseOrderTransactionEntityDataProvider {

    private final PurchaseOrderDaoEntity purchaseOrderDaoEntity;

    public PurchaseOrderTransactionEntityPhysicalDatabaseProvider(PurchaseOrderDaoEntity purchaseOrderDaoEntity) {
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
