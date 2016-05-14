package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.USCoin;
import main.java.product.Product;
import main.java.product.JunkFood;

public class USVendingMachine implements VendingMachine {

	// Represents that little sticker that shows accepted currency
	public static final Coin[] VALID_PAYMENT_OPTIONS = { USCoin.NICKEL,
			USCoin.DIME, USCoin.QUARTER };

	// Represents the buttons on the machine
	public static final Product[] AVAILABLE_SELECTIONS = {
			JunkFood.COLA, JunkFood.CHIPS,
			JunkFood.CANDY };

	// Vending Machine makers know coin measurements from the US mint so why
	// shouldn't we refer to the USCoin class? Maybe to avoid tight coupling
	// between the components...
	private Display display;
	private List<Coin> insertedCoins;
	private List<Coin> coinReturn;

	private boolean hasValidCoinMeasurements(Coin coin) {
		// This could be spoofed, but at that point the spoofer deserves a candy bar
		for (Coin usCoin : USCoin.values()) {
			if (coin.mass() == usCoin.mass()
					&& coin.thickness() == usCoin.thickness()
					&& coin.diameter() == usCoin.diameter()) {
				// Found a match, good enough 
				return true;
			}
		}
		return false;
	}

	private BigDecimal totalCoinSum(List<Coin> coins) {
		BigDecimal total = BigDecimal.ZERO;
		for (Coin coin : coins) {
			total = total.add(coin.value());
		}
		return total;
	}
	
	
	public USVendingMachine() {
		this.display = new EnglishDisplay();
		this.insertedCoins = new ArrayList<Coin>();
		this.coinReturn = new ArrayList<Coin>();
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
			this.display.setToInsertedTotal(this.totalCoinSum(this.insertedCoins));
		} else {
			this.coinReturn.add(coin);
		}
		return isValidCoin;
	}

	@Override
	public String displayMessage() {
		String message = this.display.currentMessage();
		if (!this.insertedCoins.isEmpty()){
			this.display.setToInsertedTotal(this.totalCoinSum(this.insertedCoins));
		} else {
			this.display.reset();
		}
		return message;
	}

	@Override
	public List<Coin> clearCoinReturn() {
		List<Coin> tempCoins = new ArrayList<Coin>(this.coinReturn);
		this.coinReturn.clear();
		return tempCoins;
	}

	@Override
	public boolean selectProduct(Product selection) {
		boolean shouldDispense = false;
		if (this.totalCoinSum(this.insertedCoins).compareTo(selection.price()) >= 0){
			shouldDispense = true;
			this.display.setToGratefulMessage();
			this.insertedCoins.clear();
		} else {
			this.display.setToPrice(selection.price());
		}
		return shouldDispense;
	}

}
