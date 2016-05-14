package test.java.vendingmachine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestEnglishDisplay.class,
	TestUSCoinBank.class,
	})
public class VendingMachineTestSuite {
	
}
