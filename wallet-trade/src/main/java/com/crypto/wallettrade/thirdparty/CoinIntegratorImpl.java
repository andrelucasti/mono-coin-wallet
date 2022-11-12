package com.crypto.wallettrade.thirdparty;

import com.crypto.wallettrade.business.coin.Coin;
import com.crypto.wallettrade.business.coin.CoinIntegrator;
import com.crypto.wallettrade.business.coin.CurrencyType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinIntegratorImpl implements CoinIntegrator {

    @Override
    public Optional<Coin> findBy(String symbol) {

        var coin = new Coin("BTC", "Bitcoin", 17000D, CurrencyType.USD);

        return Optional.of(coin);
    }
}