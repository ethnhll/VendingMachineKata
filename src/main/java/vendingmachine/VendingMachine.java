package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;
import main.java.product.Product;

public interface VendingMachine {

	
	boolean insertCoin(Coin coin);
	
	String displayMessage();
	
	List<Coin> clearCoinReturn();
	
	boolean selectProduct(Product product);

}
