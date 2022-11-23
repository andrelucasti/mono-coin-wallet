package com.crypto.wallettransaction.thirdparty.coinMarketCap;

import com.crypto.wallettransaction.business.coin.Coin;
import com.crypto.wallettransaction.business.coin.CoinIntegrator;
import com.crypto.wallettransaction.business.coin.CurrencyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:application-wallet-transaction.properties")
public class CoinIntegrationCoinMarketCap implements CoinIntegrator {

    private final String token;
    private final CoinMarketCapClient coinMarketCapClient;

    public CoinIntegrationCoinMarketCap(@Value("${feign.client.coinMarketCap.token}") String token,
                                        CoinMarketCapClient coinMarketCapClient) {
        this.token = token;
        this.coinMarketCapClient = coinMarketCapClient;
    }

    @Override
    public Optional<Coin> findBy(String symbol) {
        return coinMarketCapClient.findCoinBy(token, symbol)
                .map(coinMarketCapResponse -> {
                    var coinData = coinMarketCapResponse.coinData().get(symbol);
                    var priceQuote = coinData.quote().get("USD");

                    return new Coin(coinData.symbol(), coinData.name(), priceQuote.price(), CurrencyType.USD);
                });
    }
}
