package main.java.vendingmachine;

import java.math.BigDecimal;

/**
 * A representation of a {@code VendingMachine}'s external display which can be
 * made to show various information to a user interacting with the machine.
 * 
 * @see main.java.vendingmachine.VendingMachine
 */
public interface Display {

	/**
	 * Restores this {@code Display} to an initial state. The nature of the
	 * initial state for a {@code Display} varies by implementation.
	 */
	void reset();

	/**
	 * Sets the current message of this {@code Display} to be one of gratitude.
	 * The nature of the grateful message for a {@code Display} varies by
	 * implementation.
	 */
	void setToGratefulMessage();

	/**
	 * Sets the current message of this {@code Display} to be one that indicates
	 * a machine is out of stock of an item. The nature of an out of stock
	 * message for a {@code Display} varies by implementation.
	 */
	void setToOutOfStockMessage();

	/**
	 * Sets the current message of this {@code Display} to be one that reports
	 * the price of an item offered by a machine. The nature of the displayed
	 * price for a {@code Display} varies by implementation of the currency for
	 * the locale of the machine.
	 * 
	 * @see main.java.currency.Coin
	 */
	void setToPrice(BigDecimal value);

	/**
	 * Sets the current message of this {@code Display} to match the monetary
	 * value of the currently inserted total currency in a machine. The nature
	 * of the format for the inserted total for a {@code Display} varies by
	 * implementation of the currency for the locale of the machine.
	 * 
	 * @see main.java.currency.Coin
	 */
	void setToInsertedTotal(BigDecimal total);

	/**
	 * Sets the current message of this {@code Display} to be one that reports
	 * that a machine cannot make change and thus requires exact change to
	 * inserted. The nature of the exact change message for a {@code Display}
	 * varies by implementation.
	 */
	void setToExactChangeMessage();

	/**
	 * Reports the currently displaying message.
	 * 
	 * @return the currently displayed message of this {@code Display}
	 */
	String currentMessage();

}
