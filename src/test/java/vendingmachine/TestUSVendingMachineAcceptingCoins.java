package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestUSVendingMachineAcceptingCoins {

	VendingMachine machineUnderTest;
	List<Coin> validPaymentOptions;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Coin[] foreignCoins = ForeignCoin.values();

	@Before
	public void beforeTesting() {
		this.machineUnderTest = new USVendingMachine();
		this.validPaymentOptions = Arrays.asList(USVendingMachine.VALID_PAYMENT_OPTIONS);
	}

	// No coins insert tests
	@Test
	public void testNoCoinsInsertedDisplayShowsInsertCoin() {
		// Given no coins have been inserted
		String currentMessage = this.machineUnderTest.currentDisplayMessage();
		// Then the display should show "INSERT COIN"
		assertThat(currentMessage, is(equalTo("INSERT COINS")));
	}

	@Test
	public void testNoCoinsInsertedCoinReturnIsEmpty() {
		// Given no coins have been inserted
		List<Coin> coinReturnContents = this.machineUnderTest
				.clearCoinReturn();
		// Then no coins should be in the coin return
		assertThat(coinReturnContents.isEmpty(), is(true));
	}
	
	@Test
	public void testCurrentInsertedTotalIsZeroWhenNoCoinsAreInserted() {
		// Given no coins have been inserted
		// Then the current inserted total should be zero
		assertThat(this.machineUnderTest.currentInsertedTotal(), is(BigDecimal.ZERO));
	}

	// Single invalid coin tests
	@Theory
	public void testIsInsertedInvalidCoinRejected(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given coin is inserted
		boolean wasRejected = !this.machineUnderTest.insertCoin(invalidCoin);
		// Then the coin should have been rejected
		assertThat(wasRejected, is(true));
	}

	@Theory
	public void testRejectedCoinInCoinReturn(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given coin is inserted and assuming it was rejected
		assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
		// Then, coin return should contain the rejected coin
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		assertThat(returnedCoins, hasItem(invalidCoin));
		// And the rejected coin only
		assertThat(returnedCoins.size(), is(1));
	}

	@Theory
	public void testInsertingInvalidCoinDoesNotChangeDisplay(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// The original message before any changes
		String expectedMessage = this.machineUnderTest.currentDisplayMessage();
		// Given coin is inserted and assuming it was rejected
		assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
		// Then the display message should not have changed
		assertThat(this.machineUnderTest.currentDisplayMessage(),
				is(equalTo(expectedMessage)));
	}
	
	@Theory
	public void testCurrentInsertedTotalIsZeroAfterInvalidCoinInserted(Coin invalidCoin){
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given coin is inserted and assuming it was rejected
		assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
		// Then the current inserted total should not have changed
		assertThat(this.machineUnderTest.currentInsertedTotal(),
				is(equalTo(BigDecimal.ZERO)));
	}

	// Multiple invalid coins tests
	@Theory
	public void testAreInsertedInvalidCoinsRejected(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given that invalid coins are inserted
		int numCoins = 10;
		List<Boolean> rejectionNotices = new ArrayList<Boolean>();
		for (int i = 0; i < numCoins; i += 1) {
			boolean wasRejected = !this.machineUnderTest.insertCoin(invalidCoin);
			// Assuming the coin was rejected
			assumeTrue(wasRejected);
			rejectionNotices.add(wasRejected);
		}
		// Then all of the coins should have been rejected
		assertThat(rejectionNotices, everyItem(is(true)));
	}

	@Theory
	public void testRejectedCoinsInCoinReturn(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given that invalid coins are inserted
		int numCoins = 10;
		List<Coin> expectedCoins = new ArrayList<Coin>();
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was rejected
			assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
			expectedCoins.add(invalidCoin);
		}
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the same rejected coins
		assertThat(returnedCoins.containsAll(expectedCoins), is(true));
	}

	@Theory
	public void testInsertingInvalidCoinsDidNotChangeDisplay(Coin invalidCoin) {
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// The original message before any changes
		String expectedMessage = this.machineUnderTest.currentDisplayMessage();
		// Given that invalid coins are inserted
		int numCoins = 10;
		List<String> messages = new ArrayList<String>();
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was rejected
			assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
			messages.add(this.machineUnderTest.currentDisplayMessage());
		}
		// Then, the display message should not have ever changed
		assertThat(messages, everyItem(is(equalTo(expectedMessage))));
	}
	
	@Theory
	public void testCurrentInsertedTotalIsZero(Coin invalidCoin){
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given that invalid coins are inserted
		int numCoins = 10;
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was rejected
			assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
		}
		// Then the current inserted total should not have changed
		assertThat(this.machineUnderTest.currentInsertedTotal(),
				is(equalTo(BigDecimal.ZERO)));
	}

	// Single valid coin tests
	@Theory
	public void testIsInsertedValidCoinAccepted(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given coin is inserted
		boolean wasAccepted = this.machineUnderTest.insertCoin(validCoin);
		// Then the coin should have been accepted
		assertThat(wasAccepted, is(true));
	}

	@Theory
	public void testInsertedValidCoinNotInCoinReturn(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		// Then, coin return should not contain the inserted coin
		assertThat(returnedCoins, not(hasItem(validCoin)));
		// And no other coins either
		assertThat(returnedCoins.size(), is(0));
	}

	@Theory
	public void testInsertingValidCoinChangesDisplay(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// The original message before any changes
		String previousMessage = this.machineUnderTest.currentDisplayMessage();
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		// Then the display should now be different than it was before
		assertThat(this.machineUnderTest.currentDisplayMessage(),
				is(not(equalTo(previousMessage))));
	}

	@Theory
	public void testDisplayMatchesValueAfterInsertingValidCoin(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		String formattedValue = String.format("$%s", validCoin.value()
				.toString());
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		// Then the display should match the monetary value of the coin
		assertThat(this.machineUnderTest.currentDisplayMessage(),
				is(equalTo(formattedValue)));
	}
	
	@Theory
	public void testCurrentInsertedTotalMatchesCoinAfterInsertingValidCoin(Coin validCoin){
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		// Then the current inserted total should match the monetary value of the coin
		assertThat(this.machineUnderTest.currentInsertedTotal(),
				is(equalTo(validCoin.value())));
	}

	// Multiple valid coins tests
	@Theory
	public void testAreInsertedCoinsAccepted(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given that valid coins are inserted
		int numCoins = 10;
		List<Boolean> acceptanceNotices = new ArrayList<Boolean>();
		for (int i = 0; i < numCoins; i += 1) {
			boolean wasAccepted = this.machineUnderTest.insertCoin(validCoin);
			// Assuming the coin was accepted
			assumeTrue(wasAccepted);
			acceptanceNotices.add(wasAccepted);
		}
		// Then all of the coins should have been accepted
		assertThat(acceptanceNotices, everyItem(is(true)));
	}

	@Theory
	public void testAcceptedCoinsNotInCoinReturn(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given that valid coins are inserted
		int numCoins = 10;
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		}
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		// Then the coin return shouldn't contain anything
		assertThat(returnedCoins.size(), is(0));
	}

	@Theory
	public void testInsertingValidCoinsChangesDisplay(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// The original message before any changes
		String firstMessage = this.machineUnderTest.currentDisplayMessage();
		// Given that valid coins are inserted
		int numCoins = 10;
		List<String> messages = new ArrayList<String>();
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
			messages.add(this.machineUnderTest.currentDisplayMessage());
		}
		// Then, the display message should have changed every time
		assertThat(messages, everyItem(is(not(equalTo(firstMessage)))));
	}
	
	@Theory
	public void testDisplayMatchesValueAfterInsertingValidCoins(Coin validCoin) {
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Ten of the coins
		BigDecimal coinsValue = validCoin.value().multiply(BigDecimal.TEN);
		String formattedValue = String.format("$%s", coinsValue.toString());
		// Given that valid coins are inserted
		int numCoins = 10;
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		}
		assertThat(this.machineUnderTest.currentDisplayMessage(),
				is(equalTo(formattedValue)));
	}
	
	@Theory
	public void testCurrentInsertedTotalMatchesAllCoinsAfterInsertingValidCoins(Coin validCoin){
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Ten of the coins
		BigDecimal coinsValue = validCoin.value().multiply(BigDecimal.TEN);
		// Given that valid coins are inserted
		int numCoins = 10;
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		}
		assertThat(this.machineUnderTest.currentInsertedTotal(),
				is(equalTo(coinsValue)));
	}

}
