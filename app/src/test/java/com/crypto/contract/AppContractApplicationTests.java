package com.crypto.contract;

import com.crypto.walletmanager.dataprovider.portfolio.WalletManagerPortfolioEntityDAO;
import com.crypto.wallettransaction.dataprovider.portfolio.WalletTransactionPortfolioEntityDAO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AppContractApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WalletManagerPortfolioEntityDAO walletManagerPortfolioEntityDAO;

    @Autowired
    private WalletTransactionPortfolioEntityDAO walletTransactionPortfolioEntityDAO;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        walletTransactionPortfolioEntityDAO.deleteAll();
        walletManagerPortfolioEntityDAO.deleteAll();

    }
}
