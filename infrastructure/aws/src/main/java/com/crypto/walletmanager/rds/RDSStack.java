package com.crypto.walletmanager.rds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.rds.CfnDBInstance;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class RDSStack {

    private static final String DB_SECRET_NAME = "walletManagerDBSecret";
    private static final String RDS_ENGINE = "postgres";
    private static final String RDS_ENGINE_VERSION = "14";
    private static final String RDS_US_EAST_1 = "us-east-1d";
    private static final String DB_NAME = "walletManagerDatabase";
    private static final String RDS_DB_TYPE = "db.t4g.micro";

    private final Stack scope;

    public RDSStack(Stack scope) {
        this.scope = scope;
    }

    public void execute(){

        try{
            String secretString = getSecretValueAsString();

            ObjectMapper objectMapper = new ObjectMapper();
            DBSecretManagerDTO dbSecretManagerDTO = objectMapper.readValue(secretString, DBSecretManagerDTO.class);

              CfnDBInstance.Builder.create(scope, "wallet-manager-database")
                      .engine(RDS_ENGINE)
                      .engineVersion(RDS_ENGINE_VERSION)
                      .publiclyAccessible(true)
                      .storageEncrypted(false)
                      .allocatedStorage("30")
                      .availabilityZone(RDS_US_EAST_1)
                      .dbName(DB_NAME)
                      .dbInstanceClass(RDS_DB_TYPE)
                      .deleteAutomatedBackups(true)
                      .masterUsername(dbSecretManagerDTO.username)
                      .masterUserPassword(dbSecretManagerDTO.password)
                      .build();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String getSecretValueAsString() {
        GetSecretValueRequest walletManagerDBSecret =
                GetSecretValueRequest
                        .builder()
                        .secretId(RDSStack.DB_SECRET_NAME)
                        .build();
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
        GetSecretValueResponse secretValue = secretsManagerClient.getSecretValue(walletManagerDBSecret);

        return secretValue.secretString();
    }

    protected AwsCredentialsProvider getAwsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Getter
    @ToString
    private static class DBSecretManagerDTO {

        private final String username;
        private final String password;

        public DBSecretManagerDTO(@JsonProperty("username") String username,
                                  @JsonProperty("password") String password) {
            this.username = username;
            this.password = password;
        }
    }
}
