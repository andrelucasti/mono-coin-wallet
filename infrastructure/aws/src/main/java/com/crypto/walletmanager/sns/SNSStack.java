package com.crypto.walletmanager.sns;

import software.amazon.awscdk.services.sns.Topic;
import software.constructs.Construct;
public class SNSStack {

    private final Construct scope;
    public SNSStack(Construct scope) {
        this.scope = scope;
    }

    public void execute(){
        Topic.Builder.create(scope, "wallet-manager-portfolio")
                .fifo(false)
                .contentBasedDeduplication(false)
                .topicName("wallet-manager-portfolio")
                .build();
    }
}
