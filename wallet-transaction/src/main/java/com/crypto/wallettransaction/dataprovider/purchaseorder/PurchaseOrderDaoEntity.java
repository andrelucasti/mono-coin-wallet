package com.crypto.wallettransaction.dataprovider.purchaseorder;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PurchaseOrderDaoEntity extends CrudRepository<PurchaseOrderTransactionEntity, UUID> {
}
