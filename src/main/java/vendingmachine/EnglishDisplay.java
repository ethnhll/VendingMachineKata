package main.java.vendingmachine;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class EnglishDisplay implements Display{
	
	private static final String INSERT_COINS = "INSERT COINS";
	private static final String GRATEFUL = "THANK YOU";
	private static final String OUT_OF_STOCK = "OUT OF STOCK";
	private static final String EXACT_CHANGE = "EXACT CHANGE ONLY";
	private static final String PRICE_PREFIX = "PRICE ";
	
	private String currentMessage;
	
	public EnglishDisplay(){
		this.currentMessage = INSERT_COINS;
	}

	@Override
	public void reset() {
		this.currentMessage = INSERT_COINS;
	}

	@Override
	public void setToGratefulMessage() {
		this.currentMessage = GRATEFUL;
		
	}

	@Override
	public void setToOutOfStockMessage() {
		this.currentMessage = OUT_OF_STOCK;
		
	}

	@Override
	public void setToPrice(BigDecimal value) {
		String dollarValueString = NumberFormat.getCurrencyInstance(Locale.US).format(value);
		this.currentMessage = String.join(PRICE_PREFIX, dollarValueString);
	}

	@Override
	public void setToInsertedTotal(BigDecimal total) {
		this.currentMessage = NumberFormat.getCurrencyInstance(Locale.US).format(total);
		
	}

	@Override
	public void setToExactChangeMessage() {
		this.currentMessage = EXACT_CHANGE;
		
	}

	@Override
	public String currentMessage() {
		return this.currentMessage;
	}

}
