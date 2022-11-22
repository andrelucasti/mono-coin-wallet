package com.crypto.wallettransaction.business.coin;

public record Coin(String symbol, String name, double currentValue, CurrencyType currencyType) {
}
