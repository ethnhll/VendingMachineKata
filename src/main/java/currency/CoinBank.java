package main.java.currency;

import java.math.BigDecimal;
import java.util.List;

/**
 * A simple {@code Coin} holder that can accept and dispense {@code Coin}s
 * (provided there are enough in stock). The type of {@code Coin} that is held
 * in this {@code CoinBank} depends on the implementation.
 * 
 * @see main.java.currency.Coin
 * 
 */
public interface CoinBank {

	/**
	 * Inserts a {@code Coin} into this {@code CoinBank}'s hopper, where it is
	 * held until a call to {@code addInsertedCoinsToStock} dispense the
	 * inserted {@code Coin}s.
	 * 
	 * @param coin
	 *            the {@code Coin} to be inserted into this {@code CoinBank}
	 */
	void insertCoin(Coin coin);

	/**
	 * Reports the total monetary value of all of the inserted {@code Coin}s in
	 * this {@code CoinBank}'s hopper.
	 * 
	 * @return the monetary value of the total inserted {@code Coin}s
	 */
	BigDecimal insertedTotal();

	/**
	 * Ejects the inserted {@code Coin}s from this {@code CoinBank}'s hopper.
	 * 
	 * @return the ejected {@code Coin}s from this {@code CoinBank}'s hopper
	 */
	List<Coin> returnInsertedCoins();

	/**
	 * Dispenses the inserted {@code Coin}s in this {@code CoinBank}'s hopper
	 * into an internal stock store.
	 */
	void addInsertedCoinsToStock();

	/**
	 * Reports whether or not this {@code CoinBank} can dispense change using
	 * coin's from an internal stock.
	 * 
	 * @param value
	 *            the total value of the {@code Coin}s expected to be dispensed
	 * @return true if this {@code CoinBank} can dispense {@code value} in
	 *         change, false otherwise
	 */
	boolean canMakeChange(BigDecimal value);

	/**
	 * Reports a list of {@code Coin}s that total to {@code value} which were
	 * dispensed from the internal stock of this {@code CoinBank}. An empty list
	 * indicates that this {@code CoinBank} could not make change the specified
	 * {@code value}.
	 * 
	 * @param value
	 *            the total monetary value of the {@code Coin}s expected to be
	 *            dispensed
	 * @return a list of dispensed {@code Coin}s
	 */
	List<Coin> makeChange(BigDecimal value);

}
