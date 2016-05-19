package main.java.currency;

import java.util.Comparator;

public class CoinComparator implements Comparator<Coin> {

	@Override
	public int compare(Coin coin, Coin otherCoin) {
		return coin.value().compareTo(otherCoin.value());
	}
	
	@Override
	public boolean equals(Object obj){
		return false;
	}

}
