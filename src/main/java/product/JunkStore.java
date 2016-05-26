package main.java.product;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JunkStore implements ProductStore{
	
	private Map<JunkFood, Integer> stock;
	
	public JunkStore(){
		this.stock = new EnumMap<>(JunkFood.class);
	}

	@Override
	public void addToStock(Product product) {
		if (!(product instanceof JunkFood)){
			throw new IllegalArgumentException("Unrecognized product added");
		}
		if (!this.stock.containsKey(product)){
			this.stock.put((JunkFood) product, 1);
		} else {
			Integer numInStock = this.stock.get(product) + 1;
			this.stock.put((JunkFood) product, numInStock);
		}
	}

	@Override
	public boolean dispense(Product product) {
		if (!(product instanceof JunkFood)){
			throw new IllegalArgumentException("Unrecognized product selected");
		}
		boolean dispensed = false;
		if (this.stock.containsKey(product) && this.stock.get(product) > 0){
			dispensed = true;
			Integer numInStock = this.stock.get(product) - 1;
			this.stock.put((JunkFood) product, numInStock);
		}
		return dispensed;
	}

	@Override
	public boolean isInStock(Product product) {
		boolean isInStock = false;
		if (this.stock.containsKey(product) && this.stock.get(product) > 0){
			isInStock = true;
		}
		return isInStock;
	}

	@Override
	public Set<Product> availableSelections() {
		Set<Product> available = new HashSet<Product>();
		for (Product product : this.stock.keySet()){
			if (this.stock.get(product) > 0){
				available.add(product);
			}
		}
		return available;
	}

}
