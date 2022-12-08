package com.crypto.walletmanager.infrastructure;


import com.amazonaws.services.sns.AmazonSNS;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AmazonSNSConfiguration {
    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS){
        return new NotificationMessagingTemplate(amazonSNS, logicalResourceId -> {
            log.info("Sending message to topic - ".concat(logicalResourceId));
            return logicalResourceId;
        });
    }
}
