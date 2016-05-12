package main.java.machine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;

public interface VendingMachine {

	
	boolean insertCoin(Coin currency);
	
	String currentDisplayMessage();
	
	BigDecimal currentInsertedTotal();
	
	List<Coin> clearCoinReturnTray();

}
