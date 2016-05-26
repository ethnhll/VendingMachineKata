package test.java.vendingmachine;

import static org.junit.Assert.*;

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
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import test.java.testutils.VendingMachineUtils;
@RunWith(Theories.class)
public class TestUSVendingMachineShowingSoldOut {

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
}
