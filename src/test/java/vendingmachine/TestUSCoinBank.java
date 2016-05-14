package test.java.vendingmachine;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;

import main.java.currency.Coin;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;
import main.java.vendingmachine.CoinBank;
import main.java.vendingmachine.USCoinBank;

import org.junit.Before;
import org.junit.Test;

public class TestUSCoinBank {

	CoinBank bankUnderTest;
	
	@Before
	public void beforeTesting(){
		this.bankUnderTest = new USCoinBank();
	}
	
	
	@Test
	public void insertCoin_NewInstanceUSCoins_ShouldAcceptCoin() {
		// Given all the USCoins inserted
		for(Coin coin : USCoin.values()){
			this.bankUnderTest.insertCoin(coin);
		}
		// Then no problems should arise
		assertTrue(true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertCoin_NewInstanceForeignCoins_ShouldThrowException() {
		// Given all the ForeignCoins inserted
		for(Coin coin : ForeignCoin.values()){
			this.bankUnderTest.insertCoin(coin);
		}
	}

	@Test
	public void testMakeChange() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddInsertedCoinsToStock() {
		fail("Not yet implemented");
	}

	@Test
	public void insertedTotal_NoCoinsInserted_TotalShouldBeZero() {
		// Given no coins are inserted
		BigDecimal total = this.bankUnderTest.insertedTotal();
		// Then the total should be zero
		assertThat(total.compareTo(BigDecimal.ZERO), is(0));
	}
	
	@Test
	public void insertedTotal_USCoinsInserted_TotalShouldMatchCoinsTotal() {
		// Given that US coins are inserted
		BigDecimal coinTotal = BigDecimal.ZERO;
		for (Coin coin : USCoin.values()){
			coinTotal = coinTotal.add(coin.value());
			this.bankUnderTest.insertCoin(coin);
		}
		BigDecimal bankTotal = this.bankUnderTest.insertedTotal();
		// Then the total should match the total of the coins inserted
		assertThat(bankTotal.compareTo(coinTotal), is(0));
	}

	@Test
	public void testReturnInsertedCoins() {
		fail("Not yet implemented");
	}

}
