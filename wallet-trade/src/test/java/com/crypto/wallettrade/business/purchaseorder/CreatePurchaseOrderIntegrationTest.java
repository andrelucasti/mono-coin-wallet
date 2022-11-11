package com.crypto.wallettrade.business.purchaseorder;

import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDAOImp;
import com.crypto.walletmanager.dataprovider.portfolio.PortfolioDataProviderInMemory;
import com.crypto.wallettrade.business.coin.Coin;
import com.crypto.wallettrade.business.coin.CurrencyType;
import com.crypto.wallettrade.business.wallet.PortfolioNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

class CreatePurchaseOrderIntegrationTest {


    private CreatePurchaseOrder createPurchaseOrder;

    @BeforeEach
    void setUp() {
        var portfolioDAO = new PortfolioDAOImp(new PortfolioDataProviderInMemory());
        createPurchaseOrder = new CreatePurchaseOrder(portfolioDAO);
    }

    @Test
    void shouldNotCreateAPurchaseOrderWhenWalletDoesNotExist() {
        var walletId = UUID.randomUUID();
        var btc = new Coin("BTC", "Bitcoin", 17000D, CurrencyType.USD);
        var purchaseOrder = new PurchaseOrder(walletId, "BTC", 1D, 0, ZonedDateTime.now(), btc);


        Assertions.assertThrows(PortfolioNotFoundException.class, ()-> createPurchaseOrder.execute(purchaseOrder));
    }
}