package com.crypto.wallettransaction.thirdparty.walletManager;


import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "classpath:application-wallet-transaction.properties")
public class PortfolioConsumer {

    @SqsListener(value = {"${cloud.aws.sqs.from.wallet-manager.queue-name}"})
    public void consumer(String message){

        System.out.println(message);
    }
}
