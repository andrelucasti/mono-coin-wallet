package com.crypto.wallettransaction.sqs;

import com.crypto.wallettransaction.sqs.queues_subscribed.QueuesSubscribedStack;


public class SQSStack  {
    private final QueuesSubscribedStack queuesSubscribedStack;


    public SQSStack(QueuesSubscribedStack queuesSubscribedStack) {
        this.queuesSubscribedStack = queuesSubscribedStack;

    }

    public void execute() {
        queuesSubscribedStack.execute();
    }
}