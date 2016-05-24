package main.java.currency;

import java.math.BigDecimal;
import java.util.List;


public interface CoinBank {
	
	void insertCoin(Coin coin);
	BigDecimal insertedTotal();
	List<Coin> returnInsertedCoins();
	void addInsertedCoinsToStock();
	List<Coin> payout(BigDecimal value);
	
}
