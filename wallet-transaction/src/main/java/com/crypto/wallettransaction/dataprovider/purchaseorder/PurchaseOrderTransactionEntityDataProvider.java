package com.crypto.wallettransaction.dataprovider.purchaseorder;

import java.util.List;

public interface PurchaseOrderTransactionEntityDataProvider {
    void save(PurchaseOrderTransactionEntity purchaseOrderTransactionEntity);
    List<PurchaseOrderTransactionEntity> findAll();
}
