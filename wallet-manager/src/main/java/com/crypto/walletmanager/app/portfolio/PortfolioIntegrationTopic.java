package com.crypto.walletmanager.app.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioIntegration;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

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
        var portfolioMessage = MessageBuilder
                .withPayload(portfolioDTO)
                .build();

        notificationMessagingTemplate.convertAndSend(topicName, portfolioMessage);
    }
}
