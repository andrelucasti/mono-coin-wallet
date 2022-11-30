package com.crypto.integration.walletmanager;


import com.crypto.walletmanager.dataprovider.portfolio.WalletManagerPortfolioEntityDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class AbstractWalletManagerIntegrationTest {

    @Autowired
    private WalletManagerPortfolioEntityDAO walletManagerPortfolioEntityDAO;

    @BeforeEach
    void setUp() {
        walletManagerPortfolioEntityDAO.deleteAll();
    }

    protected AwsCredentialsProvider getAwsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    protected SqsClient sqsClient() throws URISyntaxException {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(new URI("http://localhost:4566"))
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
    }

    protected ReceiveMessageResponse receiveMessage(String queueUrl) throws URISyntaxException {
        var sqsClient = sqsClient();
        var receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        return sqsClient.receiveMessage(receiveMessageRequest);
    }
}
