package com.crypto.wallettrade.business.coin;

public class CoinNotFoundException extends Exception {
    public CoinNotFoundException(String message) {
        super(message);
    }

    public CoinNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
