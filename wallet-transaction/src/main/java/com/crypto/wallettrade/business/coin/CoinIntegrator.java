package com.crypto.wallettrade.business.coin;

import java.util.Optional;

public interface CoinIntegrator {

    Optional<Coin> findBy(String symbol);
}
