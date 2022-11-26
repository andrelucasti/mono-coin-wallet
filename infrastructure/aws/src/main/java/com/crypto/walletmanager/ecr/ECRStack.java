package com.crypto.walletmanager.ecr;

import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.TagMutability;
import software.constructs.Construct;

public class ECRStack{

    private final Construct scope;

    public ECRStack(Construct scope) {
        this.scope = scope;
    }

    public void execute(){
        Repository.Builder.create(scope, "wallet-manager-registry")
                .repositoryName("wallet-manager")
                .imageTagMutability(TagMutability.IMMUTABLE)
                .build();
    }
}
