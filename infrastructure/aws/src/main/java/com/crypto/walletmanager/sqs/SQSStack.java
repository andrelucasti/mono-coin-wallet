package com.crypto.walletmanager.sqs;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.sqs.DeadLetterQueue;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

public class SQSStack  {
    private final Construct scope;

    public SQSStack(Construct scope) {
        this.scope = scope;
    }

    public void execute() {
        /**
            wallet-manager-portfolio-to-wallet-transaction
         */
        Queue.Builder.create(scope, "wallet-manager-portfolio-to-wallet-transaction")
                .queueName("wallet-manager-portfolio-to-wallet-transaction")
                .visibilityTimeout(Duration.seconds(300))
                .deadLetterQueue(
                        DeadLetterQueue.builder()
                                .maxReceiveCount(3)
                                .queue(Queue.Builder.create(scope, "wallet-manager-portfolio-to-wallet-transaction-dlq")
                                        .queueName("wallet-manager-portfolio-to-wallet-transaction-dlq")
                                        .visibilityTimeout(Duration.seconds(600))
                                        .build())
                                .build()
                )
                .build();
    }
}
