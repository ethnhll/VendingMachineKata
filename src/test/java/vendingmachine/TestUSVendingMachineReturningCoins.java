package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestUSVendingMachineReturningCoins {
	
	VendingMachine machineUnderTest;
	
	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Coin[] foreignCoins = ForeignCoin.values();
	
	@Before
	public void beforeTesting() {
		this.machineUnderTest = new USVendingMachine();
	}

	@Test
	public void pressCoinReturn_NoCoinsInserted_CoinReturnShouldBeEmpty() {
		// Given no coins are inserted and the coin return is pressed
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should be empty
		assertThat(coinReturn.isEmpty(), is(true));
	}
	
	@Theory
	public void pressCoinReturn_USCoinInserted_CoinReturnShouldContainCoin(Coin usCoin) {
		assumeTrue(usCoin instanceof USCoin);
		// Given coin is inserted (assuming coin is accepted) and the coin return is pressed
		assumeTrue(this.machineUnderTest.insertCoin(usCoin));
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the coin
		assertThat(coinReturn, everyItem(is(equalTo(usCoin))));
	}
	
	@Test
	public void pressCoinReturn_USCoinsInserted_CoinReturnShouldContainCoins() {
		// Given coins are inserted (assuming coins are accepted) and the coin return is pressed
		List<Coin> insertedCoins = new ArrayList<Coin>();
		for (Coin coin : USCoin.values()){
			assumeTrue(this.machineUnderTest.insertCoin(coin));			
			insertedCoins.add(coin);
		}
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the coins
		assertThat(coinReturn.containsAll(insertedCoins), is(true));
	}
	
	@Theory
	public void pressCoinReturn_ForeignCoinInserted_CoinReturnShouldContainCoin(Coin foreignCoin) {
		assumeTrue(!(foreignCoin instanceof USCoin));
		// Given coin is inserted (assuming coin is rejected) and the coin return is pressed
		assumeFalse(this.machineUnderTest.insertCoin(foreignCoin));
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the coin
		assertThat(coinReturn, everyItem(is(equalTo(foreignCoin))));
	}
	
	@Test
	public void pressCoinReturn_ForeignCoinsInserted_CoinReturnShouldContainCoins() {
		// Given coins are inserted (assuming coins are rejected) and the coin return is pressed
		List<Coin> insertedCoins = new ArrayList<Coin>();
		for (Coin coin : ForeignCoin.values()){
			assumeFalse(this.machineUnderTest.insertCoin(coin));			
			insertedCoins.add(coin);
		}
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain the coins
		assertThat(coinReturn.containsAll(insertedCoins), is(true));
	}
	
	@Test
	public void pressCoinReturn_MixedCoinsInserted_CoinReturnShouldContainCoins() {
		// Given foreign coins are inserted (assuming they are rejected)
		List<Coin> insertedCoins = new ArrayList<Coin>();
		for (Coin coin : ForeignCoin.values()){
			assumeFalse(this.machineUnderTest.insertCoin(coin));			
			insertedCoins.add(coin);
		}
		// and USCoins are inserted (assuming they are accepted)
		for (Coin coin : USCoin.values()){
			assumeTrue(this.machineUnderTest.insertCoin(coin));			
			insertedCoins.add(coin);
		}
		// and the coin return button is pressed
		this.machineUnderTest.pressCoinReturn();
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should contain all the inserted coins
		assertThat(coinReturn.containsAll(insertedCoins), is(true));
	}

}
