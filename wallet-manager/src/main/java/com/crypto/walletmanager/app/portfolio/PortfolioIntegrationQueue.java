package com.crypto.walletmanager.app.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import com.crypto.walletmanager.business.portfolio.PortfolioIntegration;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "classpath:application-wallet-manager.properties")
public class PortfolioIntegrationQueue implements PortfolioIntegration {

    private final String queueName;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public PortfolioIntegrationQueue(@Value("${cloud.aws.sqs.to.wallet-transaction.queue-name}") String queueName,
                                     QueueMessagingTemplate queueMessagingTemplate) {
        this.queueName = queueName;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @Override
    public void send(Portfolio portfolio) {
        Message<Portfolio> portfolioMessage = MessageBuilder
                .withPayload(portfolio)
                .build();

        queueMessagingTemplate.send(queueName,  portfolioMessage);
    }
}

