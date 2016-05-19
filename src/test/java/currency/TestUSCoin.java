package test.java.currency;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;

import main.java.currency.USCoin;

import org.junit.Test;

public class TestUSCoin {

	@Test
	public void testGetDenominationPenny() {
		BigDecimal pennyValue = new BigDecimal("0.01");
		assertThat(USCoin.PENNY.value(), is(equalTo((pennyValue))));
	} 
	
	@Test
	public void testGetDenominationNickel() {
		BigDecimal nickelValue = new BigDecimal("0.05");
		assertThat(USCoin.NICKEL.value(), is(equalTo((nickelValue))));
	}
	
	@Test
	public void testGetDenominationDime() {
		BigDecimal dimeValue = new BigDecimal("0.10");
		assertThat(USCoin.DIME.value(), is(equalTo((dimeValue))));
	} 

	@Test
	public void testGetDenominationQuarter() {
		BigDecimal quarterValue = new BigDecimal("0.25");
		assertThat(USCoin.QUARTER.value(), is(equalTo((quarterValue))));
	}
}
