package test.java.machine;

import main.java.machine.USVendingMachine;
import main.java.machine.VendingMachine;

import org.junit.Before;

public class TestUSVendingMachineSelectingProducts {
	VendingMachine machineUnderTest;
	
	@Before
	public void beforeTesting() {
		this.machineUnderTest = new USVendingMachine();
	}
}
