package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;

public interface CoinBank {
	
	boolean add(Coin coin);
	BigDecimal insertedTotal();
	List<Coin> makeChange(BigDecimal value);
	
}
