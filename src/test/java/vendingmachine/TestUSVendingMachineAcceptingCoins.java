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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.ProductStore;
import main.java.vendingmachine.EnglishDisplay;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import test.java.testutils.VendingMachineTestUtils;

@RunWith(Theories.class)
public class TestUSVendingMachineAcceptingCoins {

	
	List<Coin> validPaymentOptions;
	VendingMachine machineUnderTest;
	static final int NUM_TEST_COINS = 10;
	
	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Coin[] foreignCoins = ForeignCoin.values();

	@Before
	public void beforeTesting() {
		CoinBank usCoinBank = VendingMachineTestUtils.stockUSCoinBank();
		ProductStore junkStore = VendingMachineTestUtils.stockJunkStore();

		this.machineUnderTest = new USVendingMachine(new EnglishDisplay(),
				(USCoinBank) usCoinBank, junkStore);
		this.validPaymentOptions = Arrays.asList(USVendingMachine.VALID_PAYMENT_OPTIONS);
	}

	// =====================
	// No coins insert tests
	// =====================
	@Test
	public void displayMessage_NoCoinsInserted_DisplayShouldShowInsertCoins() {
		// Given no coins have been inserted
		String currentMessage = this.machineUnderTest.displayMessage();
		// Then the display should show "INSERT COIN"
		assertThat(currentMessage, is(equalTo("INSERT COINS")));
	}

	@Test
	public void clearCoinReturn_NoCoinsInserted_CoinReturnShouldBeEmpty() {
		// Given no coins have been inserted
		List<Coin> coinReturnContents = this.machineUnderTest.clearCoinReturn();
		// Then no coins should be in the coin return
		assertThat(coinReturnContents.isEmpty(), is(true));
	}

	// =========================
	// Single invalid coin tests
	// =========================
	@Theory
	public void insertCoin_InvalidCoinInserted_CoinShouldBeRejected(Coin invalidCoin) {
		// Assuming the coin is invalid
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given coin is inserted
		boolean wasRejected = !this.machineUnderTest.insertCoin(invalidCoin);
		// Then the coin should have been rejected
		assertThat(wasRejected, is(true));
	}

	@Theory
	public void clearCoinReturn_InvalidCoinInserted_CoinShouldBeInCoinReturn(Coin invalidCoin) {
		// Assuming the coin is invalid
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
	public void displayMessage_InvalidCoinInserted_DisplayShouldNotChange(Coin invalidCoin) {
		// Assuming the coin is invalid
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// The original message before any changes
		String expectedMessage = this.machineUnderTest.displayMessage();
		// Given coin is inserted and assuming it was rejected
		assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
		// Then the display message should not have changed
		assertThat(this.machineUnderTest.displayMessage(),
				is(equalTo(expectedMessage)));
	}

	// ============================
	// Multiple invalid coins tests
	// ============================
	@Theory
	public void insertCoin_InvalidCoinsInserted_InvalidCoinsShouldBeRejected(Coin invalidCoin) {
		// Assuming the coin is invalid
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given that invalid coins are inserted
		List<Boolean> rejectionNotices = new ArrayList<Boolean>();
		for (int i = 0; i < NUM_TEST_COINS; i += 1) {
			boolean wasRejected = !this.machineUnderTest.insertCoin(invalidCoin);
			// Assuming the coin was rejected
			assumeTrue(wasRejected);
			rejectionNotices.add(wasRejected);
		}
		// Then all of the coins should have been rejected
		assertThat(rejectionNotices, everyItem(is(true)));
	}

	@Theory
	public void clearCoinReturn_InvalidCoinsInserted_CoinsShoudBeInCoinReturn(Coin invalidCoin) {
		// Assuming the coin is invalid
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// Given that invalid coins are inserted
		List<Coin> expectedCoins = new ArrayList<Coin>();
		for (int i = 0; i < NUM_TEST_COINS; i += 1) {
			// Assuming the coin was rejected
			assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
			expectedCoins.add(invalidCoin);
		}
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the same rejected coins
		assertThat(returnedCoins.containsAll(expectedCoins), is(true));
	}

	@Theory
	public void displayMessage_InvalidCoinsInserted_DisplayShouldNotChange(Coin invalidCoin) {
		// Assuming the coin is invalid
		assumeThat(this.validPaymentOptions, not(hasItem(invalidCoin)));
		// The original message before any changes
		String originalMessage = this.machineUnderTest.displayMessage();
		// Given that invalid coins are inserted
		int numCoins = 10;
		List<String> messages = new ArrayList<String>();
		for (int i = 0; i < numCoins; i += 1) {
			// Assuming the coin was rejected
			assumeFalse(this.machineUnderTest.insertCoin(invalidCoin));
			messages.add(this.machineUnderTest.displayMessage());
		}
		// Then, the display message should not have ever changed
		assertThat(messages, everyItem(is(equalTo(originalMessage))));
	}

	// =======================
	// Single valid coin tests
	// =======================
	@Theory
	public void insertCoin_ValidCoinInserted_CoinShouldBeAccepted(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given coin is inserted
		boolean wasAccepted = this.machineUnderTest.insertCoin(validCoin);
		// Then the coin should have been accepted
		assertThat(wasAccepted, is(true));
	}

	@Theory
	public void clearCoinReturn_ValidCoinInserted_CoinShouldNotBeInCoinReturn(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		List<Coin> returnedCoins = this.machineUnderTest.clearCoinReturn();
		// Then coin return should not contain the inserted coin
		assertThat(returnedCoins, not(hasItem(validCoin)));
		// And no other coins either
		assertThat(returnedCoins.size(), is(0));
	}

	@Theory
	public void displayMessage_ValidCoinInserted_DisplayShouldBeDifferent(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// The original message before any changes
		String previousMessage = this.machineUnderTest.displayMessage();
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		// Then the display should now be different than it was before
		assertThat(this.machineUnderTest.displayMessage(),
				is(not(equalTo(previousMessage))));
	}

	@Theory
	public void displayMessage_ValidCoinInserted_DisplayMatchesCoinValue(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		String coinValue = NumberFormat.getCurrencyInstance(Locale.US)
				.format(validCoin.value());
		// Given coin is inserted and assuming it was accepted
		assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		// Then the display should match the monetary value of the coin
		assertThat(this.machineUnderTest.displayMessage(),
				is(equalTo(coinValue)));
	}

	// ==========================
	// Multiple valid coins tests
	// ==========================
	@Theory
	public void insertCoin_ValidCoinsInserted_CoinsShouldBeAccepted(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// Given that valid coins are inserted
		List<Boolean> acceptanceNotices = new ArrayList<Boolean>();
		for (int i = 0; i < NUM_TEST_COINS; i += 1) {
			boolean wasAccepted = this.machineUnderTest.insertCoin(validCoin);
			// Assuming the coin was accepted
			assumeTrue(wasAccepted);
			acceptanceNotices.add(wasAccepted);
		}
		// Then all of the coins should have been accepted
		assertThat(acceptanceNotices, everyItem(is(true)));
	}

	@Theory
	public void clearCoinReturn_ValidCoinsInserted_CoinsShouldNotBeInCoinReturn(Coin validCoin) {
		// Assuming the coin is valid
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
	public void displayMessage_ValidCoinsInserted_DisplayShouldBeDifferent(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// The original message before any changes
		String firstMessage = this.machineUnderTest.displayMessage();
		// Given that valid coins are inserted
		List<String> messages = new ArrayList<String>();
		for (int i = 0; i < NUM_TEST_COINS; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
			messages.add(this.machineUnderTest.displayMessage());
		}
		// Then, the display message should have changed every time
		assertThat(messages, everyItem(is(not(equalTo(firstMessage)))));
	}
	
	@Theory
	public void displayMessage_ValidCoinsInserted_DisplayMatchesCoinsValue(Coin validCoin) {
		// Assuming the coin is valid
		assumeThat(this.validPaymentOptions, hasItem(validCoin));
		// int would get cast to float and float to BigDecimal is ugly... 10 != 9.9999999999990
		BigDecimal numCoins = new BigDecimal(Integer.toString(NUM_TEST_COINS));
		BigDecimal coinsValue = validCoin.value().multiply(numCoins);
		String formattedValue = NumberFormat.getCurrencyInstance(Locale.US).format(coinsValue);
		// Given that valid coins are inserted
		for (int i = 0; i < NUM_TEST_COINS; i += 1) {
			// Assuming the coin was accepted
			assumeTrue(this.machineUnderTest.insertCoin(validCoin));
		}
		assertThat(this.machineUnderTest.displayMessage(),
				is(equalTo(formattedValue)));
	}

}
