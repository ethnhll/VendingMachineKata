package test.java.vendingmachine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestUSVendingMachineAcceptingCoins.class,
	TestUSVendingMachineSelectingProducts.class,
	TestUSVendingMachineReturningCoins.class,
	})
public class VendingMachineFeatureTestSuite {

}
