package com.crypto.wallettrade.business.coin;

public record Coin(String symbol, String name, double currentValue, CurrencyType currencyType) {
}
