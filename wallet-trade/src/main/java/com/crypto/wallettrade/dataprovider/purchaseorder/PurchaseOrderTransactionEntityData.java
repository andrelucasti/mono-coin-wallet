package com.crypto.wallettrade.dataprovider.purchaseorder;

import java.util.List;

public interface PurchaseOrderTransactionEntityData {
    void save(PurchaseOrderTransactionEntity purchaseOrderTransactionEntity);
    List<PurchaseOrderTransactionEntity> findAll();
}
