package com.crypto.walletmanager.rds;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.rds.DatabaseCluster;

public class RDSStack {

    private final Stack scope;

    public RDSStack(Stack scope) {
        this.scope = scope;
    }

    public void execute(){

        //TODO RDS Postgresql here
    }
}
