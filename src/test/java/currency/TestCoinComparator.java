package test.java.currency;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;

import main.java.currency.Coin;
import main.java.currency.CoinComparator;
import main.java.currency.ForeignCoin;
import main.java.currency.USCoin;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestCoinComparator {

	Comparator<Coin> comparatorUnderTest;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@Before
	public void initialize() {
		this.comparatorUnderTest = new CoinComparator();
	}

	@Theory
	public void compare_SameCoinInstance_ShouldReturnZero(Coin coin) {
		assertThat(comparatorUnderTest.compare(coin, coin), is(equalTo(0)));
	}

	@Test
	public void compare_USCoinsEqual_ShouldReturnZero() {
		Coin coin = USCoin.PENNY;
		Coin otherCoin = USCoin.PENNY;
		assertThat(comparatorUnderTest.compare(coin, otherCoin), is(equalTo(0)));
	}

	@Test
	public void compare_FirstUSCoinIsGreater_ShouldReturnOne() {
		Coin coin = USCoin.NICKEL;
		Coin otherCoin = USCoin.PENNY;
		assertThat(comparatorUnderTest.compare(coin, otherCoin), is(equalTo(1)));
	}

	@Test
	public void compare_FirstUSCoinIsLesser_ShouldReturnNegativeOne() {
		Coin coin = USCoin.PENNY;
		Coin otherCoin = USCoin.NICKEL;
		assertThat(comparatorUnderTest.compare(coin, otherCoin),
				is(equalTo(-1)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void compare_FirstCoinIsADifferentType_ShouldThrowException() {
		Coin coin = ForeignCoin.FOOBAR;
		Coin otherCoin = USCoin.NICKEL;
		comparatorUnderTest.compare(coin, otherCoin);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void compare_OtherCoinIsADifferentType_ShouldThrowException() {
		Coin coin = USCoin.NICKEL;
		Coin otherCoin = ForeignCoin.FOOBAR;
		comparatorUnderTest.compare(coin, otherCoin);
	}

}
