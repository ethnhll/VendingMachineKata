package main.java.currency;

import java.math.BigDecimal;

public enum ForeignCoin implements Coin {
	
	// Just make it huge
	FOOBAR("10", 10F, 10F, 10F);

	private BigDecimal value;
	private float mass;
	private float thickness;
	private float diameter;

	private ForeignCoin(String value, float mass, float thickness, float diameter) {
		BigDecimal decimal = new BigDecimal(value);
		// Wouldn't want our taxes to be UNDER paid, now would we?
		decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		this.value = decimal;
		this.mass = mass;
		this.thickness = thickness;
		this.diameter = diameter;
	}
	
	@Override
	public BigDecimal value() {
		return this.value;
	}

	@Override
	public float mass() {
		return this.mass;
	}

	@Override
	public float thickness() {
		return this.thickness;
	}

	@Override
	public float diameter() {
		return this.diameter;
	}

}
