package test.java.vendingmachine;

import java.util.Arrays;
import java.util.List;

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
	
	// TODO: These test names are getting ridiculous, let's refactor them after this suite is done...
	
	VendingMachine machineUnderTest;
	List<Product> availableSelections;
	
	@DataPoints
	public static Product[] products = JunkFood.values();
	
	@Before
	public void beforeTesting() {
		this.machineUnderTest = new USVendingMachine();
		this.availableSelections = Arrays.asList(USVendingMachine.AVAILABLE_SELECTIONS);
	}
	// Assuming that the customer can't press buttons that don't exist
	
	// No coins inserted at all
	@Theory
	public void testPressButtonNoCoinsInsertedNoProductDispensed(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		// Then no product is dispensed
		assertThat(wasDispensed, is(false));
	}
	
	@Theory
	public void testPressButtonNoCoinsInsertedDisplayChanged(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Then display should be different
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo("INSERT COINS"))));
	}
	
	@Theory
	public void testPressButtonNoCoinsInsertedDisplayMatchesSelectionPrice(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		String message = this.machineUnderTest.currentDisplayMessage();
		assumeThat(message, is(not(equalTo("INSERT COINS"))));
		String priceFormat = String.format("PRICE $%s",selection.price());
		// Then message should match the price of the selection
		assertThat(message, is(equalTo(priceFormat)));
	}
	
	@Theory
	public void testPressButtonNoCoinsInsertedDisplayChangedAgainAfterChecking(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that no coins were inserted and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo("INSERT COINS"))));
		// Then a subsequent check should show the original message
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(equalTo("INSERT COINS")));
	}
	
	// Not enough money inserted...
	@Theory
	public void testPressButtonNotEnoughCoinsInsertedNoProductDispensed(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted
		this.machineUnderTest.insertCoin(USCoin.NICKEL);
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		// Then no product is dispensed
		assertThat(wasDispensed, is(false));
	}
	
	@Theory
	public void testPressButtonNotEnoughCoinsInsertedDisplayChanged(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted
		this.machineUnderTest.insertCoin(USCoin.NICKEL);
		String message = this.machineUnderTest.currentDisplayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Then display should be different than it was before
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo(message))));
	}
	
	@Theory
	public void testPressButtonNotEnoughCoinsInsertedDisplayMatchesSelectionPrice(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted
		this.machineUnderTest.insertCoin(USCoin.NICKEL);
		String originalMessage = this.machineUnderTest.currentDisplayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		String message = this.machineUnderTest.currentDisplayMessage();
		assumeThat(message, is(not(equalTo(originalMessage))));
		String priceFormat = String.format("PRICE $%s",selection.price());
		// Then message should match the price of the selection
		assertThat(message, is(equalTo(priceFormat)));
	}
	
	@Theory
	public void testPressButtonNotEnoughCoinsInsertedDisplayChangedAgainAfterChecking(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Assume that the selection costs more than the smallest acceptable coin, the Nickel
		assumeTrue(selection.price().compareTo(USCoin.NICKEL.value()) > 0);
		// Given that not enough money was inserted
		this.machineUnderTest.insertCoin(USCoin.NICKEL);
		String originalMessage = this.machineUnderTest.currentDisplayMessage();
		// and assuming that no product is dispensed
		assumeFalse(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo(originalMessage))));
		// Then a subsequent check should show the original message
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(equalTo(originalMessage)));
	}
	
	
	// Enough coins are inserted...
	@Theory
	public void testPressButtonEnoughCoinsInsertedProductIsDispensed(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		// TODO
		// Then pressing the button dispenses the product
		boolean wasDispensed = this.machineUnderTest.selectProduct(selection);
		assertThat(wasDispensed, is(true));
	}
	
	@Theory
	public void testPressButtonEnoughCoinsInsertedDisplayChanged(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		// TODO
		String messageBefore = this.machineUnderTest.currentDisplayMessage();
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		
		// Then the display should be different than it was before
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo(messageBefore))));
	}
	
	@Theory
	public void testPressButtonEnoughCoinsInsertedDisplayShowsGratefulMessage(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		// Given that the inserted total is at least the price of the selection
		// TODO
		String messageBefore = this.machineUnderTest.currentDisplayMessage();
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		// and assuming the display should be different than it was before
		String currentMessage = this.machineUnderTest.currentDisplayMessage();
		assumeThat(currentMessage, is(not(equalTo(messageBefore))));
		// Then the message should show a grateful message
		assertThat(currentMessage, is(equalTo("THANK YOU")));
	}
	
	@Theory
	public void testPressButtonEnoughCoinsInsertedDisplayChangedAgainAfterChecking(Product selection){
		assumeThat(this.availableSelections, hasItem(selection));
		String originalMessage = this.machineUnderTest.currentDisplayMessage();
		// Given that the inserted total is at least the price of the selection
		// TODO
		// and assuming that pressing the button dispenses the product
		assumeTrue(this.machineUnderTest.selectProduct(selection));
		// Assume that display should be different
		assumeThat(this.machineUnderTest.currentDisplayMessage(), is(not(equalTo(originalMessage))));
		// Then a subsequent check should show the default message
		assertThat(this.machineUnderTest.currentDisplayMessage(), is(equalTo(originalMessage)));
	}
}
