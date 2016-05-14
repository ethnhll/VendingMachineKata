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
	private CoinBank coinBank;
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
	
	public USVendingMachine() {
		this.display = new EnglishDisplay();
		this.coinBank = new USCoinBank();
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
			//this.insertedCoins.add(coin);
			this.coinBank.insertCoin(coin);
			this.display.setToInsertedTotal(this.coinBank.insertedCoinTotal());
		} else {
			this.coinReturn.add(coin);
		}
		return isValidCoin;
	}

	@Override
	public String displayMessage() {
		String message = this.display.currentMessage();
		BigDecimal insertedTotal = this.coinBank.insertedCoinTotal();
		if (insertedTotal.compareTo(BigDecimal.ZERO) > 0){
			// there are coins that have been inserted
			this.display.setToInsertedTotal(insertedTotal);
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
		BigDecimal insertedTotal = this.coinBank.insertedCoinTotal();
		if (insertedTotal.compareTo(selection.price()) >= 0){
			shouldDispense = true;
			this.display.setToGratefulMessage();
			this.coinBank.addInsertedCoinsToStock();
		} else {
			this.display.setToPrice(selection.price());
		}
		return shouldDispense;
	}

}
