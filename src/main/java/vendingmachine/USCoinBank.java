package main.java.vendingmachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import main.java.currency.Coin;
import main.java.currency.USCoin;

public class USCoinBank implements CoinBank {
	
	private List<Coin> insertedCoins;
	private Map<USCoin, Integer> coinStock;
	
	public USCoinBank(){
		this.insertedCoins = new ArrayList<Coin>();
		this.coinStock = new EnumMap<>(USCoin.class);
	}
	
	@Override
	public void insertCoin(Coin coin) {
		if (coin instanceof USCoin){
			this.insertedCoins.add(coin);
		} else {
			throw new IllegalArgumentException("Unrecognized coin inserted");
		}
	}

	@Override
	public List<Coin> makeChange(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInsertedCoinsToStock() {
		for (Coin coin : this.insertedCoins){
			// Just in case...
			if (!(coin instanceof USCoin)){
				throw new IllegalArgumentException("Unrecognized coin type");
			}
			// Adjust the coin stock
			if(this.coinStock.containsKey(coin)){
				Integer count = this.coinStock.get(coin) + 1;
				this.coinStock.put((USCoin) coin, count);
			} else {
				this.coinStock.put((USCoin) coin, 1);
			}	
		}
		this.insertedCoins.clear();
	}

	@Override
	public BigDecimal insertedTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (Coin coin : this.insertedCoins){
			total = total.add(coin.value());
		}
		return total;
	}

	@Override
	public List<Coin> returnInsertedCoins() {
		List<Coin> temp = new ArrayList<Coin>(this.insertedCoins);
		this.insertedCoins.clear();
		return temp;
	}
	
	
}
