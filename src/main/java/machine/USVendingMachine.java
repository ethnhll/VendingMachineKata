package main.java.machine;

import java.math.BigDecimal;
import java.util.List;

import main.java.currency.Coin;
import main.java.currency.USCoin;

public class USVendingMachine implements VendingMachine {

	public static final String DEFAULT_DISPLAY_MESSAGE = "";

	public static final Coin[] VALID_PAYMENT_OPTIONS = { USCoin.NICKEL,
			USCoin.DIME, USCoin.QUARTER };

	public USVendingMachine() {
	}

	@Override
	public boolean insertCoin(Coin currency) {
		// TODO We don't care yet about this
		return false;
	}

	@Override
	public String currentDisplayMessage() {
		// TODO We don't care yet about this
		return null;
	}

	@Override
	public BigDecimal currentInsertedTotal() {
		// TODO We don't care yet about this
		return null;
	}

	@Override
	public List<Coin> clearCoinReturnTray() {
		// TODO We don't care yet about this
		return null;
	}

}
