package main.java.product;

import java.util.Set;

public interface ProductStore{
	void addToStock(Product product);
	boolean dispense(Product product);
	boolean isInStock(Product product);
	Set<Product> availableSelections();
}
