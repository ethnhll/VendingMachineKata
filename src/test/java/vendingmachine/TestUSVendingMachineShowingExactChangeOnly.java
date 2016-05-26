package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import main.java.currency.Coin;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.JunkFood;
import main.java.product.Product;
import main.java.product.ProductStore;
import main.java.vendingmachine.EnglishDisplay;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

import test.java.testutils.VendingMachineTestUtils;

public class TestUSVendingMachineShowingExactChangeOnly {

	static final String EXACT_CHANGE = "EXACT CHANGE ONLY";
	static final String INSERT_COINS = "INSERT COINS";

	VendingMachine machineUnderTest;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Product[] products = JunkFood.values();

	@Before
	public void beforeTesting() {
		ProductStore store = VendingMachineTestUtils.stockJunkStore();
		// We actually want to test that the VendingMachine's coin bank
		// doesn't have anything in stock, so don't stock up the coin bank..
		this.machineUnderTest = new USVendingMachine(new EnglishDisplay(),
				new USCoinBank(), store);
	}

	@Test
	public void displayMessage_CoinBankOutofStock_DisplayShouldShowExactChangeOnly() {
		// Given that the machine's coin bank doesn't have stock...
		// Then the display should show exact change only
		assertThat(this.machineUnderTest.displayMessage(),
				is(equalTo(EXACT_CHANGE)));
	}

}
