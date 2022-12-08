package com.crypto;

import software.constructs.Construct;

import java.util.Objects;

public class Account {
    private final Construct construct;

    public Account(Construct construct) {
        this.construct = construct;
    }

    public String getRegion() {
        String region = (String) construct.getNode().tryGetContext("region");
        Objects.requireNonNull(region, "region must be not null");

        return region;
    }

    public String getAccountId() {
        String accountId = (String) construct.getNode().tryGetContext("accountId");
        Objects.requireNonNull(accountId, "accountId must be not null");

        return accountId;
    }
}
