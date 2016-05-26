package main.java.currency;

import java.math.BigDecimal;

/**
 * A simple Coin Object which can be implemented to represent any arbitrary coin
 * type in any currency standard, and reports uniquely identifying measures for
 * use in machinery and coin stores/banks.
 * 
 * @see main.java.currency.CoinBank
 * @see main.java.vendingmachine.VendingMachine
 * 
 */
public interface Coin {

	/**
	 * Reports the mass of this {@code Coin} in grams, and can be used as a
	 * measure in conjunction with the other methods of this {@code Coin} to
	 * uniquely identify what kind of {@code Coin} this is.
	 * 
	 * @return the mass of this {@code Coin} in grams
	 */
	float mass();

	/**
	 * Reports the thickness of this {@code Coin} in millimeters, and can be
	 * used as a measure in conjunction with the other methods of this
	 * {@code Coin} to uniquely identify what kind of {@code Coin} this is.
	 * 
	 * @return the thickness of this {@code Coin} in millimeters
	 */
	float thickness();

	/**
	 * Reports the diameter of this {@code Coin} in millimeters, and can be used
	 * as a measure in conjunction with the other methods of this {@code Coin}
	 * to uniquely identify what kind of {@code Coin} this is.
	 * 
	 * @return the diameter of this {@code Coin} in millimeters
	 */
	float diameter();

	/**
	 * Reports the monetary value of this {@code Coin}. Precision of the value
	 * and what currency standard the value is reported in will vary by
	 * implementation.
	 * 
	 * @return the monetary value of this {@code Coin}
	 */
	BigDecimal value();

}
