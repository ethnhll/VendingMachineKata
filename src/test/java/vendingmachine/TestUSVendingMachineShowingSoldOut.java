package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import java.text.NumberFormat;
import java.util.Locale;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.JunkFood;
import main.java.product.JunkStore;
import main.java.product.Product;
import main.java.vendingmachine.EnglishDisplay;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import test.java.testutils.VendingMachineUtils;

@RunWith(Theories.class)
public class TestUSVendingMachineShowingSoldOut {

	static final String OUT_OF_STOCK = "OUT OF STOCK";
	static final String INSERT_COINS = "INSERT COINS";

	VendingMachine machineUnderTest;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Product[] products = JunkFood.values();

	@Before
	public void beforeTesting() {
		CoinBank usCoinBank = VendingMachineUtils.stockUSCoinBank();
		// We actually want to test for the most part that the VendingMachine
		// doesn't have anything in stock so we don't stock up the store..
		this.machineUnderTest = new USVendingMachine(new EnglishDisplay(),
				(USCoinBank) usCoinBank, new JunkStore());
	}

	@Theory
	public void displayMessage_NoCoinsInsertedNothingInStock_DisplayShouldShowOutOfStock(
			Product product) {
		// Assuming the machine does not have that product
		assumeThat(this.machineUnderTest.availableSelections(),
				not(hasItem(product)));
		// Given that a product is selected (and assuming that it is not
		// dispensed)
		assumeFalse(this.machineUnderTest.selectProduct(product));
		// then message shown should be out of stock
		assertThat(this.machineUnderTest.displayMessage(),
				is(equalTo(OUT_OF_STOCK)));
	}

	@Theory
	public void displayMessage_NoCoinsInsertedNothingInStockAlreadyCheckedDisplay_DisplayShouldInsertCoins(
			Product product) {
		// Assuming the machine does not have that product
		assumeThat(this.machineUnderTest.availableSelections(),
				not(hasItem(product)));
		// Given that a product is selected (and assuming that it is not
		// dispensed)
		assumeFalse(this.machineUnderTest.selectProduct(product));
		// assume the message shown should be out of stock
		assumeThat(this.machineUnderTest.displayMessage(),
				is(equalTo(OUT_OF_STOCK)));
		// Then the next message should be insert coins
		assumeThat(this.machineUnderTest.displayMessage(),
				is(equalTo(INSERT_COINS)));
	}

	@Theory
	public void displayMessage_CoinsInsertedNothingInStockAlreadyCheckedDisplay_DisplayShouldInsertCoins(
			Product product) {
		// Assuming the machine does not have that product
		assumeThat(this.machineUnderTest.availableSelections(),
				not(hasItem(product)));
		// Given that coins are inserted (assuming it was accepted)
		assumeTrue(this.machineUnderTest.insertCoin(USCoin.NICKEL));
		// and a product is selected (and assuming that it is not dispensed)
		assumeFalse(this.machineUnderTest.selectProduct(product));
		// assume the message shown should be out of stock
		assumeThat(this.machineUnderTest.displayMessage(),
				is(equalTo(OUT_OF_STOCK)));
		// Then the next message should the formatted dollar value of the inserted coin
		String coinValue = NumberFormat.getCurrencyInstance(Locale.US).format(
				USCoin.NICKEL.value());
		assumeThat(this.machineUnderTest.displayMessage(), is(equalTo(coinValue)));
	}
}
