package com.crypto.contract.wallettrader.purchaseorder;

import com.crypto.AppApplicationTests;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import com.crypto.wallettrade.business.coin.CurrencyType;
import com.crypto.wallettrade.business.purchaseorder.PurchaseOrderTransaction;
import com.crypto.wallettrade.dataprovider.DAO;
import com.crypto.wallettrade.dataprovider.purchaseorder.PurchaseOrderTransactionDAOImpl;
import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class PurchaseOrderTransactionContractTest extends AppApplicationTests {

    @Autowired
    private PortfolioDAO portfolioDAO;

    @Autowired
    private DAO<PurchaseOrderTransaction> purchaseOrderTransactionDAO;

    @Test
    void shouldReturnPortfolioWhenIsSaved() throws IOException {
        var userId = UUID.randomUUID();

        var portfolio = new Portfolio("Token Crypto", userId);
        portfolioDAO.save(portfolio);
        var portfolioId = portfolioDAO.findAll().stream().findAny().get().id();

        var payload = Resources
        .toString(Resources.getResource("contracts/purchase-order-request.json"), StandardCharsets.UTF_8)
        .replace("{portfolioId}", portfolioId.toString())
        .replace("{coinSymbol}", "BTC")
        .replace("{quantity}", "1")
        .replace("{fee}", "0")
        .replace("{purchaseOrderDate}", ZonedDateTime.of(LocalDateTime.of(2013, 1, 10, 12, 15), ZoneId.systemDefault()).toString());

        RestAssuredMockMvc.given()
            .contentType(ContentType.JSON)
            .body(payload)
            .post("/po-transaction")
            .then()
            .status(HttpStatus.CREATED);

        var purchaseOrderTransaction = purchaseOrderTransactionDAO.findAll().stream().findFirst().get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(portfolioId, purchaseOrderTransaction.purchaseOrder().portfolioId()),
                () -> Assertions.assertEquals("BTC", purchaseOrderTransaction.purchaseOrder().coinSymbol()),
                () -> Assertions.assertEquals(1, purchaseOrderTransaction.purchaseOrder().quantity()),
                () -> Assertions.assertEquals(0, purchaseOrderTransaction.purchaseOrder().fee()),
                () -> Assertions.assertEquals("BTC", purchaseOrderTransaction.coin().symbol()),
                () -> Assertions.assertEquals(CurrencyType.USD, purchaseOrderTransaction.coin().currencyType())
        );
    }
}