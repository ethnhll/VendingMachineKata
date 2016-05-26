package main.java.vendingmachine;

import java.util.List;
import java.util.Set;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.product.Product;
import main.java.product.ProductStore;

/**
 * A representation of a vending machine which can accept {@code Coin}s, return
 * {@code Coin}s, allow a user to select {@code Product}s, and report prices,
 * shortages, and other information to a user.
 * 
 * @see main.java.currency.Coin
 * @see main.java.currency.CoinBank
 * @see main.java.currency.Product
 * @see main.java.currency.ProductStore
 */
public interface VendingMachine {

	/**
	 * Adds a {@code Coin} to this {@code VendingMachine} and reports whether it
	 * was accepted or not. Implementations of this {@code VendingMachine} may
	 * vary in what {@code Coin}s are accepted. Unaccepted {@code Coin}s will
	 * appear in the coin return of this {@code VendingMachine}.
	 * 
	 * @param coin
	 *            the {@code Coin} to be inserted into this
	 *            {@code VendingMachine}
	 * @return true if the inserted {@code Coin} was accepted by this
	 *         {@code VendingMachine}, and false otherwise
	 */
	boolean insertCoin(Coin coin);

	/**
	 * Reports the currently displayed message of this {@code VendingMachine} to
	 * the user.
	 * 
	 * @return the current display message of this {@code VendingMachine}
	 */
	String displayMessage();

	/**
	 * Empties the currently inserted {@code Coin}s from this
	 * {@code VendingMachine} into a coin return, which needs to be cleared to
	 * retrieve the ejected {@code Coin}s.
	 */
	void pressCoinReturn();

	/**
	 * Empties the contents of this {@code VendingMachine}s coin return.
	 * 
	 * @return the list of {@code Coin} that was found in this
	 *         {@code VendingMachine}s coin return
	 */
	List<Coin> clearCoinReturn();

	/**
	 * Chooses the specified {@code product} from available {@code Product}s in
	 * the {@code ProductStore} of this {@code VendingMachine} and reports
	 * whether the {@code Product} was dispensed or not. Reasons for not
	 * dispensing the {@code Product} could vary from insufficient funds
	 * inserted to the {@code Product} being out of stock.
	 * 
	 * @param product
	 *            the {@code Product} to be dispensed
	 * @return true if the {@code Product} specified was dispensed, false
	 *         otherwise
	 */
	boolean selectProduct(Product product);

	/**
	 * Reports a set of all the available {@code Product}s in this
	 * {@code VendingMachine}. This method is analogous to the buttons on a real
	 * world vending machine.
	 * 
	 * @return a set of the available {@code Product}s to choose from
	 */
	Set<Product> availableSelections();

	/**
	 * Refills the stock of this {@code VendingMachine}'s {@code CoinBank}.
	 * 
	 * @param restock
	 *            the newly filled {@code CoinBank}
	 */
	void restockCoinBank(CoinBank restock);

	/**
	 * Refills the stock of this {@code VendingMachine}'s {@code ProductStore}.
	 * 
	 * @param restock
	 *            the newly filled {@code ProductStore}
	 */
	void restockProductStore(ProductStore restock);

}
