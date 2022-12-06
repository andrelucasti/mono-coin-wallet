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

    public PortfolioIntegrationTopic(@Value("${cloud.aws.sns.from.wallet-manager.portfolio.topic}") String topicName,
                                     NotificationMessagingTemplate notificationMessagingTemplate) {
        this.topicName = topicName;
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    @Override
    public void send(Portfolio portfolio) {
        var portfolioDTO = new PortfolioDTO(portfolio.id(), portfolio.name(), portfolio.userId());

        notificationMessagingTemplate.convertAndSend(topicName, portfolioDTO);

    }
}
