/**
 * PasswordGenerator
 * 
 * @author Taylor Scott
 * @version 
 *
 */
package model;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Generates random passwords of a user-specified length. The length may be specified on a per-password basis, using
 * {@link #generatePassword(int, int)}
 * 
 * @author Taylor Scott
 *
 */
public final class PasswordGenerator {
	private static PasswordGenerator instance;
	private SecureRandom random;
	
	public static PasswordGenerator getInstance() {
		if (instance == null)
			instance = new PasswordGenerator();
		
		return instance;
	}
	
	private PasswordGenerator() {
		this.random = new SecureRandom();
	}
	
	/**
	 * @param length
	 *            Number of characters in the returned String
	 * @param base
	 *            Radix value to use when converting to String
	 * @return A random password, using the maximum symbol set available in the given base
	 */
	public String generatePassword(int length, int base) {
		if (base <= 0)
			throw new IllegalArgumentException("base must be positive");
		
		int bitsPerCharacter = (int) Math.floor(Math.pow(base, 0.5));
		int numberOfBits = bitsPerCharacter * length;
		String password = new BigInteger(numberOfBits, random).toString(base);
		
		while (password.length() < length) {
			String prefix = generatePassword(length - password.length(), base);
			password = prefix.concat(password);
		}
		
		return password;
	}
}
