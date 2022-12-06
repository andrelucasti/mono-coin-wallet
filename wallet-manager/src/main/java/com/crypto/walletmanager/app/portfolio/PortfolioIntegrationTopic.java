package com.crypto.walletmanager.app.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioIntegration;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@PropertySource(value = "classpath:application-wallet-manager.properties")
public class PortfolioIntegrationTopic implements PortfolioIntegration {

    private final String topicName;
    private final NotificationMessagingTemplate notificationMessagingTemplate;
    private final ObjectMapper objectMapper;

    public PortfolioIntegrationTopic(@Value("${cloud.aws.sns.from.wallet-manager.portfolio.topic}") String topicName,
                                     NotificationMessagingTemplate notificationMessagingTemplate,
                                     ObjectMapper objectMapper) {
        this.topicName = topicName;
        this.notificationMessagingTemplate = notificationMessagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(Portfolio portfolio) {


        var portfolioDTO = new PortfolioDTO(portfolio.id(), portfolio.name(), portfolio.userId());
      //  notificationMessagingTemplate.convertAndSend(topicName, portfolioDTO);


        try {

            String writeValueAsString = objectMapper.writeValueAsString(portfolioDTO);
            PublishRequest build = PublishRequest.builder()
                    .topicArn("arn:aws:sns:us-east-1:000000000000:".concat(topicName))
                    .message(writeValueAsString)
                    .build();

            snsClient().publish(build);

        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    protected SnsClient snsClient() throws URISyntaxException {
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(new URI("http://localhost:4566"))
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
    }

    protected AwsCredentialsProvider getAwsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
