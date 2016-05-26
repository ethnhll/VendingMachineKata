package test.java.testutils;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.JunkFood;
import main.java.product.JunkStore;
import main.java.product.Product;
import main.java.product.ProductStore;

public class VendingMachineUtils {
	
	public static final Integer NUM_THINGS = 100;
	
	public static CoinBank stockUSCoinBank(){
		CoinBank usCoinBank = new USCoinBank();
		for (Coin coin : USCoin.values()){
			for (int i = 0; i < NUM_THINGS; i+=1){
				usCoinBank.insertCoin(coin);
			}
		}
		usCoinBank.addInsertedCoinsToStock();
		return usCoinBank;
	}
	
	public static ProductStore stockJunkStore(){
		ProductStore junkStore = new JunkStore();
		for (Product product : JunkFood.values()){
			for (int i = 0; i < NUM_THINGS; i+=1){
				junkStore.addToStock(product);
			}
		}
		return junkStore;
	}

}
