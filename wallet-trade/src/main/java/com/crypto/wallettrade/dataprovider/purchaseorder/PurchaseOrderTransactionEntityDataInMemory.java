package com.crypto.wallettrade.dataprovider.purchaseorder;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderTransactionEntityDataInMemory implements PurchaseOrderTransactionEntityData {

    private final List<PurchaseOrderTransactionEntity> memory;

    public PurchaseOrderTransactionEntityDataInMemory() {
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
