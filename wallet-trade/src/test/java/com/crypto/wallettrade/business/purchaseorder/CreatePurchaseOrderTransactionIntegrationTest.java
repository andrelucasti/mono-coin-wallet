package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioConverter;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDAOImp;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDataProviderInMemory;
import com.crypto.wallettrade.business.coin.Coin;
import com.crypto.wallettrade.business.coin.CoinIntegrator;
import com.crypto.wallettrade.business.coin.CoinNotFoundException;
import com.crypto.wallettrade.business.coin.CurrencyType;
import com.crypto.wallettrade.business.wallet.PortfolioNotFoundException;
import com.crypto.wallettrade.dataprovider.DAO;
import com.crypto.wallettrade.dataprovider.purchaseorder.PurchaseOrderTransactionDAOImpl;
import com.crypto.wallettrade.dataprovider.purchaseorder.PurchaseOrderTransactionEntityDataInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreatePurchaseOrderTransactionIntegrationTest {

    private CreatePurchaseOrderTransaction createPurchaseOrderTransaction;

    private PortfolioDAO portfolioDAO;
    private DAO<PurchaseOrderTransaction> purchaseOrderTransactionDAO;

    @Mock
    private CoinIntegrator coinIntegrator;


    @BeforeEach
    void setUp() {
        purchaseOrderTransactionDAO = new PurchaseOrderTransactionDAOImpl(new PurchaseOrderTransactionEntityDataInMemory());
        portfolioDAO = new PortfolioDAOImp(new PortfolioDataProviderInMemory(), new PortfolioConverter());
        createPurchaseOrderTransaction = new CreatePurchaseOrderTransaction(purchaseOrderTransactionDAO, portfolioDAO, coinIntegrator);
    }

    @Test
    void shouldNotCreateAPurchaseOrderWhenWalletDoesNotExists() {
        var portfolioId = UUID.randomUUID();
        var purchaseOrder = new PurchaseOrder(portfolioId, "BTC", 1D, 0, ZonedDateTime.now());


        assertThrows(PortfolioNotFoundException.class, ()-> createPurchaseOrderTransaction.execute(purchaseOrder));
    }

    @Test
    void shouldNotCreateAPurchaseOrderWhenCoinDoesNotExists() {
        var userId = UUID.randomUUID();

        portfolioDAO.save(new Portfolio("Token Games", userId));
        Portfolio portfolio = portfolioDAO.findAll().stream().findAny().get();

        var purchaseOrder = new PurchaseOrder(portfolio.id(), "ABC", 1D, 0, ZonedDateTime.now());
        assertThrows(CoinNotFoundException.class, ()-> createPurchaseOrderTransaction.execute(purchaseOrder));
    }

    @Test
    void shouldCreateAPurchaseOrder() throws CoinNotFoundException, PortfolioNotFoundException {
        var userId = UUID.randomUUID();
        var coin = new Coin("BTC", "Bitcoin", 17000D, CurrencyType.USD);

        Mockito.when(coinIntegrator.findBy(Mockito.eq("BTC"))).thenReturn(Optional.of(coin));



        portfolioDAO.save(new Portfolio("Token Games", userId));
        Portfolio portfolio = portfolioDAO.findAll().stream().findAny().get();

        var purchaseOrder = new PurchaseOrder(portfolio.id(), "BTC", 1D, 0, ZonedDateTime.now());
        createPurchaseOrderTransaction.execute(purchaseOrder);

        var purchaseOrderTransaction = purchaseOrderTransactionDAO.findAll().stream().findAny().get();


        Assertions.assertAll(
                ()-> assertThat(purchaseOrderTransaction.purchaseOrder().portfolioId()).isEqualTo(portfolio.id()),
                ()-> assertThat(purchaseOrderTransaction.purchaseOrder().coinSymbol()).isEqualTo(purchaseOrder.coinSymbol()),
                ()-> assertThat(purchaseOrderTransaction.purchaseOrder().quantity()).isEqualTo(purchaseOrder.quantity()),
                ()-> assertThat(purchaseOrderTransaction.purchaseOrder().fee()).isEqualTo(purchaseOrder.fee()),
                ()-> assertThat(purchaseOrderTransaction.coin().symbol()).isEqualTo(coin.symbol()),
                ()-> assertThat(purchaseOrderTransaction.coin().name()).isEqualTo(coin.name()),
                ()-> assertThat(purchaseOrderTransaction.coin().currentValue()).isEqualTo(coin.currentValue()),
                ()-> assertThat(purchaseOrderTransaction.coin().currencyType()).isEqualTo(coin.currencyType())
        );
    }
}