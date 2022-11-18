package com.crypto.wallettrade.dataprovider.purchaseorder;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public class PurchaseOrderTransactionEntityPhysicalDatabase implements PurchaseOrderTransactionEntityData{

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderTransactionEntityPhysicalDatabase(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public void save(PurchaseOrderTransactionEntity purchaseOrderTransactionEntity) {
        purchaseOrderRepository.save(purchaseOrderTransactionEntity);
    }

    @Override
    public List<PurchaseOrderTransactionEntity> findAll() {
        return StreamSupport.stream(purchaseOrderRepository.findAll().spliterator(), false).toList();
    }
}
