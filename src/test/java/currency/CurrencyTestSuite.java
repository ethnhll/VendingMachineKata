package test.java.currency;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestUSCoin.class,
	TestUSCoinBank.class,
	})
public class CurrencyTestSuite {

}
