package test.java.vendingmachine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
	public void testInsertedCoinTotal() {
		fail("Not yet implemented");
	}

	@Test
	public void testReturnInsertedCoins() {
		fail("Not yet implemented");
	}

}
