package com.crypto.contract.walletmanager.portfolio;

import com.crypto.AppApplicationTests;
import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioDAO;
import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

class PortfolioControllerContractTest extends AppApplicationTests {

    @Autowired
    private PortfolioDAO portfolioDAO;

    @Test
    void shouldReturnPortfolioWhenIsSaved() throws IOException {
        var userId = UUID.randomUUID();
        var payload = Resources.toString(Resources.getResource("contracts/portfolio-request.json"), StandardCharsets.UTF_8)
                .replace("{name}", "Token Wallet");

        RestAssuredMockMvc.given()
                .header("userId", userId)
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/portfolio")
                .then()
                .status(HttpStatus.CREATED);

        var portfolioList = portfolioDAO.findAll();
        
        Assertions.assertThat(portfolioList).isNotEmpty();
        Assertions.assertThat(portfolioList).hasSize(1);
        Assertions.assertThat(portfolioList.stream().findAny().get().userId()).isEqualTo(userId);
    }

    @Test
    void shouldReturnPortfolioByWalletId() throws IOException {
        var userId = UUID.randomUUID();
        var portFolioName = "My Token Game";
        portfolioDAO.save(new Portfolio(portFolioName, userId));

        MockHttpServletResponse mockHttpServletResponse = RestAssuredMockMvc.given()
            .header("userId", userId)
            .contentType(ContentType.JSON)
            .get("/portfolio")
            .getMockHttpServletResponse();

        var responseExpected = Resources.toString(Resources.getResource("contracts/portfolio-response.json"), StandardCharsets.UTF_8)
        .replace("{name}", portFolioName)
        .replace("{userId}", userId.toString());

        Assertions.assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(responseExpected).containsAnyOf(userId.toString(), portFolioName);

    }
}