package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import com.crypto.wallettrade.business.coin.CoinIntegrator;
import com.crypto.wallettrade.business.coin.CoinNotFoundException;
import com.crypto.wallettrade.business.wallet.PortfolioNotFoundException;
import com.crypto.wallettrade.dataprovider.DAO;
import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseOrderTransaction {

    private final DAO<PurchaseOrderTransaction> purchaseOrderTransactionDAO;
    private final PortfolioDAO portfolioDAO;
    private final CoinIntegrator coinIntegrator;


    public CreatePurchaseOrderTransaction(DAO<PurchaseOrderTransaction> purchaseOrderTransactionDAO,
                                          PortfolioDAO portfolioDAO,
                                          CoinIntegrator coinIntegrator) {

        this.purchaseOrderTransactionDAO = purchaseOrderTransactionDAO;
        this.portfolioDAO = portfolioDAO;
        this.coinIntegrator = coinIntegrator;
    }

    public void execute(PurchaseOrder purchaseOrder) throws PortfolioNotFoundException, CoinNotFoundException {
        var optionalPortfolio = portfolioDAO.findById(purchaseOrder.portfolioId());

        if (optionalPortfolio.isEmpty()) {
            throw new PortfolioNotFoundException(String.format("The portfolio %s not found", purchaseOrder.portfolioId()));
        }

        var coinOptional = coinIntegrator.findBy(purchaseOrder.coinSymbol());
        if (coinOptional.isEmpty()) {
            throw new CoinNotFoundException(String.format("The coin %s not found", purchaseOrder.coinSymbol()));
        }

        var purchaseOrderTransaction = new PurchaseOrderTransaction(purchaseOrder, coinOptional.get());
        purchaseOrderTransactionDAO.save(purchaseOrderTransaction);
    }
}
