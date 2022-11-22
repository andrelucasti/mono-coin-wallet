package com.crypto.wallettransaction.dataprovider.purchaseorder;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseOrderTransactionEntityDataProviderInMemory implements PurchaseOrderTransactionEntityDataProvider {

    private final List<PurchaseOrderTransactionEntity> memory;

    public PurchaseOrderTransactionEntityDataProviderInMemory() {
        this.memory = new ArrayList<>();
    }

    @Override
    public void save(PurchaseOrderTransactionEntity purchaseOrderTransactionEntity) {
        this.memory.add(purchaseOrderTransactionEntity);

    }

    @Override
    public List<PurchaseOrderTransactionEntity> findAll() {
        return memory;
    }
}
