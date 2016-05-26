package main.java.currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class USCoinBank implements CoinBank {
	
	private List<Coin> insertedCoins;
	private Map<USCoin, Integer> coinStock;
	
	
	public USCoinBank(){
		this.insertedCoins = new ArrayList<Coin>();
		this.coinStock = new EnumMap<>(USCoin.class);
	}
	
	private static BigDecimal sumOfCoins(Map<USCoin, Integer> stock){
		BigDecimal sum = BigDecimal.ZERO;
		for (Coin coinType : stock.keySet()){
			Integer numCoins = stock.get(coinType);
			BigDecimal totalForCoinType = coinType.value().multiply(new BigDecimal(numCoins));
			sum = sum.add(totalForCoinType);
		}
		return sum;
	}
	
	private static List<Coin> coinPayout(Map<USCoin, Integer> stock, BigDecimal value){
		assert(value.compareTo(BigDecimal.ZERO) >= 0);
		List<Coin> availableCoinTypes = new ArrayList<Coin>(stock.keySet());
		// We sort by value (desc) so that we can payout in the largest possible coins
		Collections.sort(availableCoinTypes, new CoinComparator());
		List<Coin> payout = new ArrayList<Coin>();
		for (Coin coinType : availableCoinTypes){
			int numCoins = stock.get(coinType);
			// try until we run out of this type of coin
			while (numCoins > 0){
				if (value.compareTo(BigDecimal.ZERO) == 0){
					// We're done
					break;
				} else if (value.compareTo(BigDecimal.ZERO) < 0){
					// We've overshot our payout, go back 
					value = value.add(coinType.value());
					payout.remove(payout.size()-1);
					numCoins += 1;
					// break out, we shouldn't try with this coin type
					break;
				} else {
					// Try to add the coin to the payout
					value = value.subtract(coinType.value());
					payout.add(coinType);
					numCoins -= 1;
				}
			}
		}
		if (value.compareTo(BigDecimal.ZERO) != 0){
			// Reset the payout list because it was only partial...
			payout.clear();
		}
		return payout;
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
	public boolean canMakeChange(BigDecimal value) {
		boolean canMakeChange = true;
		// Obviously insufficient funds
		if (sumOfCoins(this.coinStock).compareTo(value) < 0){
			canMakeChange = false;
		} 
		if (coinPayout(this.coinStock, value).isEmpty()){
			canMakeChange = false;
		}
		return canMakeChange;
	}
	
	
	
	@Override
	public List<Coin> makeChange(BigDecimal value) {
		List<Coin> changePayout = coinPayout(this.coinStock, value);
		// Update the contents of the coin stock to remove the coins in the list
		for (Coin coin : changePayout){
			Integer numCoins = this.coinStock.get(coin);
			this.coinStock.put((USCoin) coin, numCoins-1);
		}
		return changePayout;
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
