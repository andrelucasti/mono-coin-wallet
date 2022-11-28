package com.crypto;

import com.crypto.walletmanager.WalletTransactionStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Objects;

public class AwsApp {

    public static void main(final String[] args) {
        App app = new App();

        String accountId = (String) app.getNode()
                .tryGetContext("accountId");
        Objects.requireNonNull(accountId, "accountId must be not null");

        String region = (String) app.getNode()
                .tryGetContext("region");
        Objects.requireNonNull(region, "region must be not null");

        new WalletTransactionStack(app, "wallet-transaction-stack",
                    StackProps.builder().env(getEnv(accountId, region))
                            .build());
        app.synth();
    }

    public static Environment getEnv(final String accountId,
                                     final String region){

        return Environment.builder()
                .account(accountId)
                .region(region)
                .build();
    }
}

