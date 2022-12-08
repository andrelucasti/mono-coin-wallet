package com.crypto.wallettransaction;

import com.crypto.wallettransaction.sqs.SQSStack;
import com.crypto.wallettransaction.sqs.queues_subscribed.QueuesSubscribedStack;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class WalletTransactionStack extends Stack {
    public WalletTransactionStack(@Nullable final Construct scope,
                                  @Nullable final String id,
                                  @Nullable final StackProps props) {

        super(scope, id, props);

        SQSStack sqsStack = new SQSStack(
                new QueuesSubscribedStack(this, props)
        );

        sqsStack.execute();
    }
}
