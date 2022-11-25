package com.crypto;

import com.crypto.walletmanager.WalletTransactionStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AwsApp {

    public static void main(final String[] args) {
        App app = new App();
            new WalletTransactionStack(app, "wallet-transaction-stack", StackProps.builder().env(getEnv()).build());
        app.synth();
    }

    public static Environment getEnv(){
        return Environment.builder()
                .account("040335195619")
                .region("us-east-1")
                .build();
    }
}

