package main.java.product;

import java.util.Set;

/**
 * A simple {@code Product} holder that can accept and dispense {@code Product}s
 * (provided there are enough in stock). The type of {@code Product}s that are
 * held in this {@code ProductStore} depend on the implementation.
 * 
 * @see main.java.product.Product
 */
public interface ProductStore {
	/**
	 * Adds the {@code Product} to the internal bank of this
	 * {@code ProductStore}. Accepted {@code Product}s vary by implementation.
	 * 
	 * @param product
	 *            the {@code Product} to be inserted
	 */
	void addToStock(Product product);

	/**
	 * Dispenses a {@code Product}, provided that it is in stock, and reports
	 * whether the {@code Product} was dispensed. If false is reported, this
	 * indicates that this {@code ProductStore} does not have the
	 * {@code Product} in stock.
	 * 
	 * @param product
	 *            the requested {@code Product} to be dispensed from this
	 *            {@code ProductStore}
	 * @return true if the {@code Product} could be dispensed, and false
	 *         otherwise
	 */
	boolean dispense(Product product);

	/**
	 * Reports whether this {@code ProductStore} contains the specified
	 * {@code Product} in its stock.
	 * 
	 * @param product
	 *            the requested {@code Product} for which to check
	 * @return true if this {@code ProductStore} contains the {@code Product},
	 *         false otherwise
	 */
	boolean isInStock(Product product);

	/**
	 * Reports a set of all of the available {@code Product}s in this
	 * {@code ProductStore}. An empty set implies that no {@code Product} is in
	 * stock and that {@code Product}s should be added to this
	 * {@code ProductStore}.
	 * 
	 * @return the set of {@code Product}s in stock
	 */
	Set<Product> availableSelections();
}
