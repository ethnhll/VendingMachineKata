package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.CoinComparator;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.Product;
import main.java.product.ProductStore;

public class USVendingMachine implements VendingMachine {

	// Represents that little sticker that shows accepted currency
	public static final Coin[] VALID_PAYMENT_OPTIONS = { USCoin.NICKEL,
			USCoin.DIME, USCoin.QUARTER };

	private Display display;
	private CoinBank coinBank;
	private ProductStore productStore;
	private List<Coin> coinReturn;

	// Vending Machine makers know coin measurements from the US mint so why
	// shouldn't we refer to the USCoin class? Maybe to avoid tight coupling
	// between the components... Could maybe be deferred to the coin bank
	private boolean hasValidCoinMeasurements(Coin coin) {
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

	private BigDecimal maxChangeNeeded(Coin[] paymentOptions) {
		List<Coin> coins = Arrays.asList(paymentOptions);
		Collections.sort(coins, new CoinComparator());
		Coin biggestCoin = coins.get(0);
		Coin smallestCoin = coins.get(coins.size() - 1);
		return biggestCoin.value().subtract(smallestCoin.value());
	}

	public USVendingMachine(Display display, USCoinBank coinBank,
			ProductStore productStore) {
		this.display = display;
		this.coinBank = coinBank;
		this.productStore = productStore;
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
			// this.insertedCoins.add(coin);
			this.coinBank.insertCoin(coin);
			this.display.setToInsertedTotal(this.coinBank.insertedTotal());
		} else {
			this.coinReturn.add(coin);
		}
		return isValidCoin;
	}

	@Override
	public String displayMessage() {
		String message = this.display.currentMessage();
		BigDecimal insertedTotal = this.coinBank.insertedTotal();
		if (insertedTotal.compareTo(BigDecimal.ZERO) > 0) {
			// there are coins that have been inserted
			this.display.setToInsertedTotal(insertedTotal);
		} else if (!this.coinBank.canMakeChange(maxChangeNeeded(VALID_PAYMENT_OPTIONS))) {
			this.display.setToExactChangeMessage();
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
		if (this.productStore.isInStock(selection)) {
			// The product is available
			BigDecimal insertedTotal = this.coinBank.insertedTotal();
			if (insertedTotal.compareTo(selection.price()) == 0) {
				// Exact change inserted
				shouldDispense = true;
				this.display.setToGratefulMessage();
				this.coinBank.addInsertedCoinsToStock();
			} else if (insertedTotal.compareTo(selection.price()) > 0) {
				// Some change needs to be made
				BigDecimal payout = insertedTotal.subtract(selection.price());
				if (this.coinBank.canMakeChange(payout)) {
					shouldDispense = true;
					this.display.setToGratefulMessage();
					this.coinBank.addInsertedCoinsToStock();
					this.coinReturn.addAll(this.coinBank.makeChange(payout));
				} else {
					// Change can't be made
					shouldDispense = false;
					this.display.setToExactChangeMessage();
					this.coinReturn.addAll(this.coinBank.returnInsertedCoins());
				}
			} else {
				// Not enough money inserted
				this.display.setToPrice(selection.price());
			}
		} else {
			// Product is not available
			this.display.setToOutOfStockMessage();
		}
		return shouldDispense;
	}

	@Override
	public void pressCoinReturn() {
		List<Coin> returnedCoins = this.coinBank.returnInsertedCoins();
		this.coinReturn.addAll(returnedCoins);
		this.display.reset();
	}

	@Override
	public Set<Product> availableSelections() {
		return this.productStore.availableSelections();
	}

	@Override
	public void restockCoinBank(CoinBank restock) {
		if (!(restock instanceof USCoinBank)) {
			throw new IllegalArgumentException("Unrecognized coin bank");
		}
		this.coinBank = restock;
	}

	@Override
	public void restockProductStore(ProductStore restock) {
		this.productStore = restock;

	}

}
