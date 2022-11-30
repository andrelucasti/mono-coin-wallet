package com.crypto.walletmanager.dataprovider.portfolio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
@Transactional(transactionManager = "walletManagerTransaction")
public class PortfolioDataProviderInPhysicalDataProvider implements PortfolioDataProvider {

    private final WalletManagerPortfolioEntityDAO walletManagerPortfolioEntityDAO;
    public PortfolioDataProviderInPhysicalDataProvider(WalletManagerPortfolioEntityDAO walletManagerPortfolioEntityDAO) {
        this.walletManagerPortfolioEntityDAO = walletManagerPortfolioEntityDAO;
    }

    @Override
    public void save(PortfolioEntity portfolioEntity) {
        walletManagerPortfolioEntityDAO.save(portfolioEntity);

    }

    @Override
    public List<PortfolioEntity> findAll() {

        return StreamSupport
                .stream(walletManagerPortfolioEntityDAO
                        .findAll()
                        .spliterator(), false)
                .toList();
    }

    @Override
    public List<PortfolioEntity> findBy(UUID userId) {
        return StreamSupport
                .stream(walletManagerPortfolioEntityDAO
                        .findByUserId(userId)
                        .spliterator(), false)
                .toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return walletManagerPortfolioEntityDAO.findById(id);
    }

    @Override
    public PortfolioEntity findByUserIdAndName(UUID userId, String name) {
        return walletManagerPortfolioEntityDAO.findByUserIdAndName(userId, name);
    }
}
