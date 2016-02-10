package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.PasswordGenerator;

import org.junit.Test;

public class PasswordGeneratorTest {
	@FunctionalInterface
	private static interface Subroutine {
		void run();
	}
	
	@Test
	public void testLengthLarge() {
		PasswordGenerator generator = PasswordGenerator.getInstance();
		int length = 500;
		int base = 16;
		
		for (int i = 0; i < 100; i++) {
			assertEquals(length, generator.generatePassword(length, base).length());
		}
	}
	
	@Test
	public void testLengthExpected() {
		PasswordGenerator generator = PasswordGenerator.getInstance();
		int length = 16;
		int base = 16;
		
		for (int i = 0; i < 100; i++) {
			assertEquals(length, generator.generatePassword(length, base).length());
		}
	}
	
	@Test
	public void testLengthNegative() {
		PasswordGenerator generator = PasswordGenerator.getInstance();
		int length = -1;
		int base = 16;
		
		for (int i = 0; i < 100; i++) {
			assertTrue(exceptionThrown(() -> generator.generatePassword(length, base)));
		}
	}
	
	@Test
	public void testBaseZero() {
		PasswordGenerator generator = PasswordGenerator.getInstance();
		int length = 16;
		int base = 0;
		
		assertTrue(exceptionThrown(() -> generator.generatePassword(length, base)));
	}
	
	@Test
	public void testBaseNegative() {
		PasswordGenerator generator = PasswordGenerator.getInstance();
		int length = 16;
		int base = -1;
		
		assertTrue(exceptionThrown(() -> generator.generatePassword(length, base)));
	}
	
	/**
	 * @param subroutine Function to check; run of this method will be attempted once
	 * @return True if an exception was thrown by the subroutine, false otherwise
	 */
	private boolean exceptionThrown(Subroutine subroutine) {
		try {
			subroutine.run();
			return false;
		}
		catch (Exception e) {
			return true;
		}
	}
	
}
