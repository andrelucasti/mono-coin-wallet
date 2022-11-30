package com.crypto.wallettransaction.thirdparty.walletManager.portfolio;


import com.crypto.wallettransaction.business.portfolio.Portfolio;
import com.crypto.wallettransaction.business.portfolio.PortfolioRepository;
import com.crypto.wallettransaction.thirdparty.walletManager.ConsumerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource(value = "classpath:application-wallet-transaction.properties")
public class PortfolioConsumer {
    private final ObjectMapper objectMapper;
    private final PortfolioRepository portfolioRepository;

    public PortfolioConsumer(final PortfolioRepository portfolioRepository,
                             final ObjectMapper objectMapper) {
        this.portfolioRepository = portfolioRepository;
        this.objectMapper = objectMapper;
    }

    @SqsListener(value = {"${cloud.aws.sqs.from.wallet-manager.queue-name}"})
    public void consumer(final String message,
                         @Header("MessageId") final String senderId){
        try {
            var portfolioDTO = objectMapper.readValue(message, PortfolioDTO.class);
            portfolioRepository.save(new Portfolio(portfolioDTO.id(), portfolioDTO.name()));

        } catch (Throwable e){
            var errorMsg = String.format("got error at consumer portfolioDTO - SenderId: %s - message: %s", senderId, message);
            throw new ConsumerException(errorMsg, e);
        }
    }

    @MessageExceptionHandler(value = ConsumerException.class)
    void handlerException(final ConsumerException e){
        log.error("handlerException", e);
    }
}
