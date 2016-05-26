package main.java.currency;

import java.math.BigDecimal;

public enum USCoin implements Coin {

	PENNY("0.01", 2.5f, 19.05f, 1.52f), 
	NICKEL("0.05", 5.0f, 21.21f, 1.95f), 
	DIME("0.10", 2.268f, 17.91f, 1.35f), 
	QUARTER("0.25", 5.67f, 24.26f, 1.75f);

	private BigDecimal value;
	private float mass;
	private float thickness;
	private float diameter;

	private USCoin(String value, float mass, float thickness, float diameter) {
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
