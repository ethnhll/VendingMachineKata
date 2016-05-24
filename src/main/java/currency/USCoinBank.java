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
	
	@Override
	public void insertCoin(Coin coin) {
		if (coin instanceof USCoin){
			this.insertedCoins.add(coin);
		} else {
			throw new IllegalArgumentException("Unrecognized coin inserted");
		}
	}
	
	@Override
	public List<Coin> payout(BigDecimal value) {
		List<Coin> availableCoinTypes = new ArrayList<Coin>(this.coinStock.keySet());
		// We sort by value so that we can payout in the largest possible coins
		Collections.sort(availableCoinTypes, new CoinComparator());
		List<Coin> payout = new ArrayList<Coin>();
		for(Coin coinType : availableCoinTypes){
			Integer numAvailableCoins = this.coinStock.get(coinType);
			for (; numAvailableCoins > 0; numAvailableCoins -= 1){
				if (value.compareTo(BigDecimal.ZERO) == 0){
					// We're done
					break;
				} else if (value.compareTo(BigDecimal.ZERO) == -1) {
					// Went over, remove last item 
					payout.remove(payout.size()-1);
					// and add the coin value back to the target
					value = value.add(coinType.value());
					break;
				} else {
					// Try adding another item
					payout.add(coinType);
					value = value.subtract(coinType.value());
				}
			}
			// Update the coin stock
			this.coinStock.put((USCoin) coinType, numAvailableCoins);
		}
		return payout;
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
