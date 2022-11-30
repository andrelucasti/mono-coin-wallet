package com.crypto.wallettransaction.dataprovider.portfolio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WalletTransactionPortfolioEntityDAO extends CrudRepository<PortfolioEntity, UUID> {
}
