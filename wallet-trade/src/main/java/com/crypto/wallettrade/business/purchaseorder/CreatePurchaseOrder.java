package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import com.crypto.wallettrade.business.wallet.PortfolioNotFoundException;

import java.util.Optional;

public class CreatePurchaseOrder {

    private final PortfolioDAO portfolioDAO;

    public CreatePurchaseOrder(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public void execute(PurchaseOrder purchaseOrder) throws PortfolioNotFoundException {
        Optional<Portfolio> optionalPortfolio = portfolioDAO.findById(purchaseOrder.portfolioId());

        if (optionalPortfolio.isEmpty()) {
            throw new PortfolioNotFoundException(String.format("The portfolio %s not found", purchaseOrder.portfolioId()));
        }

    }
}
