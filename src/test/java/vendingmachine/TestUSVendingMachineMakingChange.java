package test.java.vendingmachine;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.CoinBank;
import main.java.currency.USCoin;
import main.java.currency.USCoinBank;
import main.java.product.JunkFood;
import main.java.product.Product;
import main.java.product.ProductStore;
import main.java.vendingmachine.EnglishDisplay;
import main.java.vendingmachine.USVendingMachine;
import main.java.vendingmachine.VendingMachine;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import test.java.testutils.VendingMachineUtils;

@RunWith(Theories.class)
public class TestUSVendingMachineMakingChange {

	VendingMachine machineUnderTest;

	@DataPoints
	public static Coin[] usCoins = USCoin.values();

	@DataPoints
	public static Product[] products = JunkFood.values();

	private static BigDecimal sumTotal(List<Coin> coins){
		BigDecimal sum = BigDecimal.ZERO;
		for (Coin coin : coins){
			sum = sum.add(coin.value());
		}
		return sum;
	}
	
	@Before
	public void beforeTesting() {
		CoinBank usCoinBank = VendingMachineUtils.stockUSCoinBank();
		ProductStore junkStore = VendingMachineUtils.stockJunkStore();

		this.machineUnderTest = new USVendingMachine(new EnglishDisplay(),
				(USCoinBank) usCoinBank, junkStore);
	}

	@Theory
	public void selectProduct_ExactRequiredCoinsInserted_CoinReturnShouldBeEmpty(
			Product product) {
		assumeTrue(product instanceof JunkFood);
		// Given exactly the needed value of coins is inserted
		BigDecimal totalInserted = BigDecimal.ZERO;
		Coin coin = USCoin.NICKEL;
		while (totalInserted.compareTo(product.price()) != 0) {
			this.machineUnderTest.insertCoin(coin);
			totalInserted = totalInserted.add(coin.value());
		}
		// assuming the product is available
		assumeThat(this.machineUnderTest.availableSelections(),
				hasItem(product));
		// and assuming the product should be dispensed
		assumeTrue(this.machineUnderTest.selectProduct(product));
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should be empty
		assertThat(coinReturn.isEmpty(), is(true));
	}

	@Theory
	public void selectProduct_MoreMoneyInsertedThanNeeded_CoinReturnShouldNotBeEmpty(
			Product product) {
		assumeTrue(product instanceof JunkFood);
		// Given exactly the needed value of coins is inserted
		BigDecimal totalInserted = BigDecimal.ZERO;
		Coin coin = USCoin.QUARTER;
		while (totalInserted.compareTo(product.price()) <= 0) {
			this.machineUnderTest.insertCoin(coin);
			totalInserted = totalInserted.add(coin.value());
		}
		// assuming the product is available
		assumeThat(this.machineUnderTest.availableSelections(),
				hasItem(product));
		// assuming the product should be dispensed
		assumeTrue(this.machineUnderTest.selectProduct(product));
		List<Coin> coinReturn = this.machineUnderTest.clearCoinReturn();
		// Then the coin return should not be empty
		assertThat(coinReturn.isEmpty(), is(false));
		// and the total in the return should be the difference of the price and
		// the inserted total
		BigDecimal expectedPayout = totalInserted.subtract(product.price());
		assertThat(expectedPayout.compareTo(sumTotal(coinReturn)), is(0));
		
	}
}
