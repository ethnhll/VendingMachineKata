package main.java.machine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import main.java.currency.Coin;
import main.java.currency.USCoin;

public class USVendingMachine implements VendingMachine {

	public static final String DEFAULT_DISPLAY_MESSAGE = "INSERT COIN";

	public static final Coin[] VALID_PAYMENT_OPTIONS = { USCoin.NICKEL,
			USCoin.DIME, USCoin.QUARTER };
	
	private static final String DISPLAY_OUTPUT_FORMAT = "$%s";

	// Vending Machine makers know coin measurements from the US mint so why
	// shouldn't we refer to the USCoin class? Maybe to avoid tight coupling
	// between the components...
	private String displayMessage;
	private List<Coin> insertedCoins;
	private List<Coin> coinReturn;

	public USVendingMachine() {
		this.displayMessage = DEFAULT_DISPLAY_MESSAGE;
		this.insertedCoins = new ArrayList<Coin>();
		this.coinReturn = new ArrayList<Coin>();
	}

	private boolean hasValidCoinMeasurements(Coin coin) {
		for (Coin usCoin : USCoin.values()) {
			if (coin.mass() == usCoin.mass()
					&& coin.thickness() == usCoin.thickness()
					&& coin.diameter() == usCoin.diameter()) {
				// Found a match (could be spoofed, but at that point they deserve it)
				return true;
			}
		}
		return false;
	}

	private BigDecimal totalCoinSum(List<Coin> coins){
		BigDecimal total = BigDecimal.ZERO;
		for (Coin coin : coins) {
			total = total.add(coin.value());
		}
		return total;
	}
	
	@Override
	public boolean insertCoin(Coin coin) {
		boolean isValidCoin = true;
		if (this.hasValidCoinMeasurements(coin)) {
			// Safe cast, if the shoe fits...
			USCoin usCoin = (USCoin) coin;
			if (!Arrays.asList(VALID_PAYMENT_OPTIONS).contains(usCoin)) {
				// Coin is not accepted at this vending machine
				isValidCoin = false;
			}
		} else {
			isValidCoin = false;
		}
		// Now where to put the coin...
		if (isValidCoin) {
			this.insertedCoins.add(coin);
		} else {
			this.coinReturn.add(coin);
		}
		return isValidCoin;
	}

	@Override
	public String currentDisplayMessage() {
		if (this.insertedCoins.isEmpty()) {
			this.displayMessage = DEFAULT_DISPLAY_MESSAGE;
		} else {
			BigDecimal total = this.totalCoinSum(this.insertedCoins);
			this.displayMessage = String.format(DISPLAY_OUTPUT_FORMAT, total.toString());
		}
		return this.displayMessage;
	}

	@Override
	public BigDecimal currentInsertedTotal() {
		return this.totalCoinSum(this.insertedCoins);
	}

	@Override
	public List<Coin> clearCoinReturn() {
		List<Coin> tempCoins = new ArrayList<Coin>(this.coinReturn);
		this.coinReturn.clear();
		return tempCoins;
	}

}
