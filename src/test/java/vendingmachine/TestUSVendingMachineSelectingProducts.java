package test.java.vendingmachine;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import main.java.currency.USCoin;
import main.java.product.Product;
import main.java.product.JunkFood;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestUSVendingMachineSelectingProducts {
	
	static final String INSERT_COINS_MESSAGE = "INSERT COINS";
	static final String PRICE_PREFIX = "PRICE ";
	static final String GRATEFUL_MESSAGE = "THANK YOU";
	
	// TODO: These test names are getting ridiculous, let's refactor them after this suite is done...
	
	VendingMachine machineUnderTest;
	List<Product> availableSelections;
	
	@DataPoints
	public static Product[] products = {JunkFood.CANDY};//JunkFood.values();
	
	@Before
	public void beforeTesting() {
		this.machineUnderTest = new USVendingMachine();
		this.availableSelections = Arrays.asList(USVendingMachine.AVAILABLE_SELECTIONS);
	}
	// Assuming that the customer can't press buttons that don't exist
	
	// ========================
	// No coins inserted at all
	// ========================
	@Theory
	public void selectProduct_NoCoinsInserted_NoProductShouldBeDispensed(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		// Then no product is dispensed
		assertThat(wasDispensed, is(false));
	}
	
	@Theory
	public void displayMessage_NoCoinsInsertedProductSelected_DisplayShouldBeDifferent(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Then display should be different
		assertThat(this.machineUnderTest.displayMessage(), is(not(equalTo(INSERT_COINS_MESSAGE))));
	}
	
	@Theory
	public void displayMessage_NoCoinsInsertedProductSelected_DisplayShouldMatchSelectionPrice(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		String message = this.machineUnderTest.displayMessage();
		assumeThat(message, is(not(equalTo(INSERT_COINS_MESSAGE))));
		String price = NumberFormat.getCurrencyInstance(Locale.US).format(selection.price());
		String priceFormatted = String.join(PRICE_PREFIX, price);
		// Then message should match the price of the selection
		assertThat(message, is(equalTo(priceFormatted)));
	}
	
	@Theory
	public void displayMessage_NoCoinsInsertedProductSelected_DisplayShouldRevertAfterChecking(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.displayMessage(), is(not(equalTo("INSERT COINS"))));
		// Then a subsequent check should show the original message
		assertThat(this.machineUnderTest.displayMessage(), is(equalTo("INSERT COINS")));
	}
	
	// =========================
	// Not enough money inserted
	// =========================
	@Theory
	public void selectProduct_NotEnoughCoins_NoProductShouldBeDispensed(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted, assuming the coin is accepted
		assumeTrue(this.machineUnderTest.insertCoin(USCoin.NICKEL));
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		// Then no product is dispensed
		assertThat(wasDispensed, is(false));
	}
	
	@Theory
	public void displayMessage_NotEnoughCoinsProductSelected_DisplayShouldBeDifferent(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted, assume coin is accepted
		assumeTrue(this.machineUnderTest.insertCoin(USCoin.NICKEL));
		String message = this.machineUnderTest.displayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Then display should be different than it was before
		assertThat(this.machineUnderTest.displayMessage(), is(not(equalTo(message))));
	}
	
	@Theory
	public void displayMessage_NotEnoughCoinsProductSelected_DisplayShouldMatchSelectionPrice(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted, assume coin is accepted
		assumeTrue(this.machineUnderTest.insertCoin(USCoin.NICKEL));
		String originalMessage = this.machineUnderTest.displayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		String message = this.machineUnderTest.displayMessage();
		assumeThat(message, is(not(equalTo(originalMessage))));
		String price = NumberFormat.getCurrencyInstance(Locale.US).format(selection.price());
		String priceFormatted = String.join(PRICE_PREFIX, price);
		// Then message should match the price of the selection
		assertThat(message, is(equalTo(priceFormatted)));
	}
	
	@Theory
	public void displayMessage_NotEnoughCoinsProductSelected_DisplayShouldRevertAfterChecking(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted, assume coin is accepted
		assumeTrue(this.machineUnderTest.insertCoin(USCoin.NICKEL));
		String originalMessage = this.machineUnderTest.displayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.displayMessage(), is(not(equalTo(originalMessage))));
		// Then a subsequent check should show the original message
		assertThat(this.machineUnderTest.displayMessage(), is(equalTo(originalMessage)));
	}
	
	
	// =========================
	// Enough coins are inserted
	// =========================
	@Theory
	public void selectProduct_EnoughCoins_ProductShouldBeDispensed(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		BigDecimal total = BigDecimal.ZERO;
		while (total.compareTo(selection.price()) < 0){
			// assume the coin is accepted
			assumeTrue(this.machineUnderTest.insertCoin(USCoin.QUARTER));
			total = total.add(USCoin.QUARTER.value());
		}
		// Then pressing the button dispenses the product
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		assertThat(wasDispensed, is(true));
	}
	
	@Theory
	public void displayMessage_EnoughCoinsProductSelected_DisplayShouldBeDifferent(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		BigDecimal total = BigDecimal.ZERO;
		while (total.compareTo(selection.price()) < 0){
			// assume the coin is accepted
			assumeTrue(this.machineUnderTest.insertCoin(USCoin.QUARTER));
			total = total.add(USCoin.QUARTER.value());
		}
		String messageBefore = this.machineUnderTest.displayMessage();
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		// Then the display should be different than it was before
		assertThat(this.machineUnderTest.displayMessage(), is(not(equalTo(messageBefore))));
	}
	
	@Theory
	public void displayMessage_EnoughCoinsProductSelected_DisplayShouldShowGratefulMessage(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		BigDecimal total = BigDecimal.ZERO;
		while (total.compareTo(selection.price()) < 0){
			// assume the coin is accepted
			assumeTrue(this.machineUnderTest.insertCoin(USCoin.QUARTER));
			total = total.add(USCoin.QUARTER.value());
		}
		String messageBefore = this.machineUnderTest.displayMessage();
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		// and assuming the display should be different than it was before
		String currentMessage = this.machineUnderTest.displayMessage();
		assumeThat(currentMessage, is(not(equalTo(messageBefore))));
		// Then the message should show a grateful message
		assertThat(currentMessage, is(equalTo(GRATEFUL_MESSAGE)));
	}
	
	@Theory
	public void displayMessage_EnoughCoinsProductSelected_DisplayRevertsAfterChecking(Product selection){
		// Assuming that an available item is selected
		assumeThat(this.availableSelections, hasItem(selection));
		String originalMessage = this.machineUnderTest.displayMessage();
		// Given that the inserted total is at least the price of the selection
		BigDecimal total = BigDecimal.ZERO;
		while (total.compareTo(selection.price()) < 0){
			// assume the coin is accepted
			assumeTrue(this.machineUnderTest.insertCoin(USCoin.QUARTER));
			total = total.add(USCoin.QUARTER.value());
		}
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.displayMessage(), is(not(equalTo(originalMessage))));
		// Then a subsequent check should show the default message
		assertThat(this.machineUnderTest.displayMessage(), is(equalTo(originalMessage)));
	}
}
