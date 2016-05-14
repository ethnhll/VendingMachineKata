package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import main.java.vendingmachine.Display;
import main.java.vendingmachine.EnglishDisplay;

import org.junit.Before;
import org.junit.Test;

public class TestEnglishDisplay {

	static final String INSERT_COINS_MESSAGE = "INSERT COINS";
	static final String GRATEFUL_MESSAGE = "THANK YOU";
	static final String PRICE_PREFIX = "PRICE ";

	static final String VALUE_STRING = "55.55";

	Display displayUnderTest;

	@Before
	public void beforeTesting() {
		this.displayUnderTest = new EnglishDisplay();
	}

	@Test
	public void currentMessage_NewInstance_MessageIsInsertCoins() {
		// Given no interaction happened with the display
		// Then the display should show the default message, INSERT COINS
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(INSERT_COINS_MESSAGE)));
	}

	@Test
	public void currentMessage_ResetIsPerformed_MessageIsInsertCoins() {
		// Given the display was reset
		this.displayUnderTest.reset();
		// Then the display should show the default message, INSERT COINS
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(INSERT_COINS_MESSAGE)));
	}

	@Test
	public void currentMessage_ChangedToNonDefaultThenReset_MessageShouldBeInsertCoins() {
		this.displayUnderTest.setToGratefulMessage();
		// Assuming the message is something different than the default, INSERT
		// COINS
		assumeThat(this.displayUnderTest.currentMessage(),
				is(not(equalTo(INSERT_COINS_MESSAGE))));
		// Given the display was reset
		this.displayUnderTest.reset();
		// Then the current message should be back to INSERT COINS
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(INSERT_COINS_MESSAGE)));
	}

	@Test
	public void currentMessage_ChangedToGratefulMessage_MessageIsGrateful() {
		// Given the display was set to a grateful message
		this.displayUnderTest.setToGratefulMessage();
		// Then the current message of the display should show gratitude
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(GRATEFUL_MESSAGE)));
	}

	@Test
	public void currentMessage_ChangedToPrice_MessageLooksLikeADollarValue() {
		// I'll want to get this randomly generated at some point...
		BigDecimal randomValue = new BigDecimal(VALUE_STRING).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		String dollarValue = NumberFormat.getCurrencyInstance(Locale.US)
				.format(randomValue);
		String expectedValue = String.join(PRICE_PREFIX, dollarValue);
		// Given the display was set to show the price output
		this.displayUnderTest.setToPrice(randomValue);
		// Then the current message of the display should show a price
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(expectedValue)));
	}

	@Test
	public void currentMessage_ChangedToInsertedTotal_MessageLooksLikeADollarValue() {
		BigDecimal randomValue = new BigDecimal(VALUE_STRING).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		String expectedDollarValue = NumberFormat
				.getCurrencyInstance(Locale.US).format(randomValue);
		// Given the display was set to show the inserted total output
		this.displayUnderTest.setToInsertedTotal(randomValue);
		// Then the current message should be the total dollar value
		assertThat(this.displayUnderTest.currentMessage(),
				is(equalTo(expectedDollarValue)));
	}

}
