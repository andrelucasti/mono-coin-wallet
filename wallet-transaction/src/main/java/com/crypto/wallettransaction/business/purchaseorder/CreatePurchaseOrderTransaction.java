package com.crypto.wallettransaction.business.purchaseorder;

import com.crypto.walletmanager.business.portfolio.PortfolioRepository;
import com.crypto.wallettransaction.business.coin.CoinIntegrator;
import com.crypto.wallettransaction.business.coin.CoinNotFoundException;
import com.crypto.wallettransaction.business.wallet.PortfolioNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseOrderTransaction {

    private final PurchaseOrderTransactionRepository purchaseOrderTransactionRepository;
    private final PortfolioRepository portfolioRepository;
    private final CoinIntegrator coinIntegrator;

    public CreatePurchaseOrderTransaction(PurchaseOrderTransactionRepository purchaseOrderTransactionRepository,
                                          PortfolioRepository portfolioRepository,
                                          @Qualifier("coinIntegrationCoinMarketCap") CoinIntegrator coinIntegrator) {

        this.purchaseOrderTransactionRepository = purchaseOrderTransactionRepository;
        this.portfolioRepository = portfolioRepository;
        this.coinIntegrator = coinIntegrator;
    }

    public void execute(PurchaseOrder purchaseOrder) throws PortfolioNotFoundException, CoinNotFoundException {
        var optionalPortfolio = portfolioRepository.findById(purchaseOrder.portfolioId());

        if (optionalPortfolio.isEmpty()) {
            throw new PortfolioNotFoundException(String.format("The portfolio %s not found", purchaseOrder.portfolioId()));
        }

        var coinOptional = coinIntegrator.findBy(purchaseOrder.coinSymbol());
        if (coinOptional.isEmpty()) {
            throw new CoinNotFoundException(String.format("The coin %s not found", purchaseOrder.coinSymbol()));
        }

        var purchaseOrderTransaction = new PurchaseOrderTransaction(purchaseOrder, coinOptional.get());
        purchaseOrderTransactionRepository.save(purchaseOrderTransaction);
    }
}
