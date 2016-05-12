package main.java.currency;

import java.math.BigDecimal;

public enum ForeignCoin implements Coin {
	FOOBAR;

	@Override
	public float mass() {
		// TODO We don't care yet about this
		return 0;
	}

	@Override
	public float thickness() {
		// TODO We don't care yet about this
		return 0;
	}

	@Override
	public float diameter() {
		// TODO We don't care yet about this
		return 0;
	}

	@Override
	public BigDecimal value() {
		// TODO We don't care yet about this
		return null;
	}

}
