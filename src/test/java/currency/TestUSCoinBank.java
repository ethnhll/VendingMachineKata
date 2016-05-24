package test.java.currency;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

public class TestUSCoinBank {

	CoinBank bankUnderTest;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Coin[] nonUSCoins = ForeignCoin.values();

	@Before
	public void beforeTesting() {
		this.bankUnderTest = new USCoinBank();
	}

	@Theory
	public void insertCoin_NewInstanceUSCoins_ShouldAcceptCoins(Coin coin) {
		assumeThat(coin, is(instanceOf(USCoin.class)));
		// Given a USCoin is inserted
		this.bankUnderTest.insertCoin(coin);
		// Then no exceptions should be thrown
		assertTrue(true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertCoin_NewInstanceNonUSCoins_ShouldThrowException() {
		for (Coin nonUSCoin : nonUSCoins) {
			// Given that a non-us coin is inserted
			this.bankUnderTest.insertCoin(nonUSCoin);
		}
	}

	@Test
	public void payout_NoCoinsInStock_ShouldReturnEmptyList() {
		BigDecimal changeValue = BigDecimal.ONE;
		List<Coin> payout = this.bankUnderTest.payout(changeValue);
		assertThat(payout.isEmpty(), is(true));
	}

	@Test
	public void payout_NotEnoughCoinsInStock_ShouldReturnEmptyList() {
		// Given that there is at least a coin in stock
		this.bankUnderTest.insertCoin(USCoin.PENNY);
		this.bankUnderTest.addInsertedCoinsToStock();
		// and the expected payout is larger than that coin
		BigDecimal changeValue = USCoin.QUARTER.value();
		// Then the payed out coin list should be empty
		List<Coin> payout = this.bankUnderTest.payout(changeValue);
		assertThat(payout.isEmpty(), is(true));
	}

	@Theory
	public void payout_CoinInStockMatchesPayoutValue_ShouldReturnSameCoin(Coin coin) {
		assumeThat(coin, is(instanceOf(USCoin.class)));
		// Given that there is at least a coin in stock
		this.bankUnderTest.insertCoin(coin);
		this.bankUnderTest.addInsertedCoinsToStock();
		// and the expected payout is the value of the inserted coin
		// Then the payed out coin list should contain the coin
		List<Coin> payout = this.bankUnderTest.payout(coin.value());
		assertThat(payout, hasItem(coin));
		// and that coin only
		assertThat(payout.size(), is(equalTo(1)));
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
		for (Coin coin : usCoins) {
			coinTotal = coinTotal.add(coin.value());
			this.bankUnderTest.insertCoin(coin);
		}
		BigDecimal bankTotal = this.bankUnderTest.insertedTotal();
		// Then the total should match the total of the coins inserted
		assertThat(bankTotal.compareTo(coinTotal), is(0));
	}

	@Test
	public void returnInsertedCoins_NoCoinsInserted_ShouldReturnEmptyList() {
		List<Coin> returnedCoins = this.bankUnderTest.returnInsertedCoins();
		assertThat(returnedCoins.isEmpty(), is(true));
	}

	@Test
	public void returnInsertedCoins_USCoinsInserted_ShouldReturnSameListOfCoins() {
		List<Coin> expectedCoins = Arrays.asList(usCoins);
		for (Coin coin : usCoins) {
			this.bankUnderTest.insertCoin(coin);
		}
		List<Coin> returnedCoins = this.bankUnderTest.returnInsertedCoins();
		assertThat(returnedCoins.containsAll(expectedCoins), is(true));
	}

}
