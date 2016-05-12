package test.java.machine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestUSVendingMachineAcceptingCoins.class,
	TestUSVendingMachineSelectingProducts.class,
	})
public class TestUSVendingMachineSuite {
	
}
