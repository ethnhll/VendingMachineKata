package test.java.vendingmachine;

import main.java.vendingmachine.CoinBank;
import main.java.vendingmachine.USCoinBank;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestUSCoinBank {

	CoinBank bankUnderTest;

	@Before
	public void beforeTesting() {
		this.bankUnderTest = new USCoinBank();
	}

	@Test
	public void testFail() {
		assertThat(true, is(true));
	}
}
