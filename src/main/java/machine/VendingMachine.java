package main.java.machine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;
import main.java.product.Product;

public interface VendingMachine {

	
	boolean insertCoin(Coin currency);
	
	String currentDisplayMessage();
	
	BigDecimal currentInsertedTotal();
	
	List<Coin> clearCoinReturn();
	
	boolean pressButton(Product selection);

}
