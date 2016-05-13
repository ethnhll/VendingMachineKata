package main.java.product;

import java.math.BigDecimal;

public enum JunkFood implements Product {
	COLA("1.00"),
	CHIPS("0.50"),
	CANDY("0.65");

	private BigDecimal price;
	
	private JunkFood(String price){
		this.price = new BigDecimal(price);
		this.price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	@Override
	public BigDecimal price() {
		return this.price;
	}

}
