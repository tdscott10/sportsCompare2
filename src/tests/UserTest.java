package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	private User invalidUser1;
	private User invalidUser2;
	private User invalidUser3;
	private User validUser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		
		invalidUser1 = new User("testemail1@asu.edu", "password", "passwordConfirm",
				"TestFname", "TestLname", "");
		invalidUser2 = new User("testemail1@asu.edu", "password2", "password2", "",
				"Change", "");
		invalidUser3 = new User("testemail1asu.edu", "password2", "password2", "test",
				"Change", "");
		validUser = new User("testemail1@asu.edu", "password2", "password2", "test",
				"Change", "");
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void validateUserTest() {
		assertFalse(invalidUser1.validate().isValid());
		assertFalse(invalidUser2.validate().isValid());
		assertFalse(invalidUser3.validate().isValid());
		assertTrue(validUser.validate().isValid());
	}
	
}
