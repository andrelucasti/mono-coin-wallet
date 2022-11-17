package com.crypto.wallettrade.dataprovider.purchaseorder;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
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
