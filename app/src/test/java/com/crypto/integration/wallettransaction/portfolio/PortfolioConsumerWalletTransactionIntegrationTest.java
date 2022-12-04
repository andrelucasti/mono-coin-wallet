package com.crypto.integration.wallettransaction.portfolio;


import com.crypto.integration.wallettransaction.AbstractWalletTransactionIntegrationTest;
import com.crypto.wallettransaction.business.portfolio.PortfolioRepository;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

class PortfolioConsumerWalletTransactionIntegrationTest extends AbstractWalletTransactionIntegrationTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Test
    @SneakyThrows
    void shouldCreatePortfolioWhenReceiveFromQueue(){
        var id = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var name = "Token Wallet";
        var payload = Resources.toString(Resources.getResource("contracts/portfolio-consumer-message.json"), StandardCharsets.UTF_8)
                .replace("{id}", id.toString())
                .replace("{name}", name)
                .replace("{userId}", userId.toString());


        SendMessageRequest sendMessageRequest = SendMessageRequest
                .builder()
                .queueUrl("wallet-manager-portfolio-to-wallet-transaction")
                .messageBody(payload)
                .build();

        SendMessageResponse sendMessageResponse = sqsClient().sendMessage(sendMessageRequest);
        Assertions.assertThat(sendMessageResponse.sdkHttpResponse().isSuccessful()).isTrue();


        var portfolioRepositoryAll = portfolioRepository.findAll();

        Assertions.assertThat(portfolioRepositoryAll).isNotEmpty();

        var portfolioSaved = portfolioRepositoryAll.stream().findAny().get();

        Assertions.assertThat(portfolioSaved.id()).isEqualTo(id);
        Assertions.assertThat(portfolioSaved.name()).isEqualTo(name);
    }
}