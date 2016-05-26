package main.java.currency;

import java.util.Comparator;

public class CoinComparator implements Comparator<Coin> {

	@Override
	public int compare(Coin coin, Coin otherCoin) {
		if (!coin.getClass().equals(otherCoin.getClass())){
			throw new IllegalArgumentException("Coins are not the same type, cannot compare");
		}
		// We want DESC order
		return otherCoin.value().compareTo(coin.value());
	}

}
