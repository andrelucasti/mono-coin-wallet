package com.crypto.wallettransaction.sqs.queues_subscribed;

import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class QueuesSubscribedStack {

    private final ConsumePortfolioToWalletTransaction consumePortfolioToWalletTransaction;

    public QueuesSubscribedStack(final Construct scope, final StackProps props) {
        this.consumePortfolioToWalletTransaction = new ConsumePortfolioToWalletTransaction(scope, props);
    }


    public void execute() {
        consumePortfolioToWalletTransaction.execute();
    }
}
