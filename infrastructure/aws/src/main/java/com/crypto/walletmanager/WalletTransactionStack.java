package com.crypto.walletmanager;

import com.crypto.walletmanager.sqs.SQSStack;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class WalletTransactionStack extends Stack {
    public WalletTransactionStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        SQSStack sqsStack = new SQSStack(this);
        sqsStack.execute();
    }
}
