package main.java.vendingmachine;

import java.util.List;
import java.util.Set;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.product.Product;
import main.java.product.ProductStore;

public interface VendingMachine {

	boolean insertCoin(Coin coin);

	String displayMessage();

	void pressCoinReturn();

	List<Coin> clearCoinReturn();

	boolean selectProduct(Product product);

	Set<Product> availableSelections();

	void restockCoinBank(CoinBank restock);

	void restockProductStore(ProductStore productStore);

}
