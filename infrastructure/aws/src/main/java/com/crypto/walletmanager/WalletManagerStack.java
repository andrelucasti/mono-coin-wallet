package com.crypto.walletmanager;

import com.crypto.walletmanager.sns.SNSStack;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class WalletManagerStack extends Stack {
    public WalletManagerStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        SNSStack snsStack = new SNSStack(this);
        snsStack.execute();
    }
}
