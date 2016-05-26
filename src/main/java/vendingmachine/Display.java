package main.java.vendingmachine;

import java.math.BigDecimal;

public interface Display {

	void reset();
	void setToGratefulMessage();
	void setToOutOfStockMessage();
	void setToPrice(BigDecimal value);
	void setToInsertedTotal(BigDecimal total);
	void setToExactChangeMessage();
	String currentMessage();
	
}
