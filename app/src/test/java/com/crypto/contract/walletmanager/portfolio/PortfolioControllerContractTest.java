package com.crypto.contract.walletmanager.portfolio;

import com.crypto.AppApplicationTests;
import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

class PortfolioControllerContractTest extends AppApplicationTests {

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
                .status(HttpStatus.CREATED)
                .body(Matchers.hasProperty("name"), Matchers.containsString("Token Wallet"))
                .body(Matchers.hasProperty("userId"), Matchers.hasValue(userId.toString()));
    }
}