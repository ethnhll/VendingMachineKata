package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;

public interface CoinBank {
	
	void insertCoin(Coin coin);
	BigDecimal insertedTotal();
	List<Coin> returnInsertedCoins();
	void addInsertedCoinsToStock();
	List<Coin> makeChange(BigDecimal value);
	
}
