package main.java.product;

import java.math.BigDecimal;

/**
 * A basic representation of an item to be sold.
 * 
 * @see main.java.product.ProductStore
 * @see main.java.vendingmachine.VendingMachine
 */
public interface Product {

	/**
	 * Reports the price of this {@code Product} as some monetary value. The
	 * value reported is varies by implementation.
	 * 
	 * @return the total monetary value of this {@code Product}
	 */
	BigDecimal price();
}
