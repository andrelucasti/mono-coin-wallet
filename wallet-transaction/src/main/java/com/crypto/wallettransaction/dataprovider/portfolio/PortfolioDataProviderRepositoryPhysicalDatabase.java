package com.crypto.wallettransaction.dataprovider.portfolio;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class PortfolioDataProviderRepositoryPhysicalDatabase implements PortfolioDataProviderRepository{

    private final WalletTransactionPortfolioEntityDAO walletTransactionPortfolioEntityDAO;

    public PortfolioDataProviderRepositoryPhysicalDatabase(final WalletTransactionPortfolioEntityDAO walletTransactionPortfolioEntityDAO) {
        this.walletTransactionPortfolioEntityDAO = walletTransactionPortfolioEntityDAO;
    }

    @Override
    public void save(PortfolioEntity entity) {
        walletTransactionPortfolioEntityDAO.save(entity);
    }

    @Override
    public List<PortfolioEntity> findAll() {
        return StreamSupport.stream(walletTransactionPortfolioEntityDAO.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return walletTransactionPortfolioEntityDAO.findById(id);
    }
}
