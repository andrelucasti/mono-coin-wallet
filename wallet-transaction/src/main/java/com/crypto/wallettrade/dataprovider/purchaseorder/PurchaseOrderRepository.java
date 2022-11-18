package com.crypto.wallettrade.dataprovider.purchaseorder;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrderTransactionEntity, UUID> {
}
