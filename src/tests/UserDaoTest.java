package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import service.AbstractDaoFactory;
import service.DaoFactory;
import service.UserDao;

public class UserDaoTest {
	private UserDao dao;
	private User user1;
	private User user2;
	private User nullUser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		AbstractDaoFactory daoFact = DaoFactory.getDaoFactory();
		
		this.dao = daoFact.getUserDao();
		
		this.user1 = new User("testemail1@asu.edu", "password", "TestFname", "TestLname",
				"");
		this.user2 = new User("testemail1@asu.edu", "password2", "Change", "Change", "");
		this.nullUser = new User(null, null, null, null, null);
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void createUserTest() {
		assertTrue(dao.createUser(user1));
	}
	
	@Test
	public void passwordMatchTest() {
		assertTrue(dao.passwordMatch("password", "password"));
		assertFalse(dao.passwordMatch("null", null));
		assertFalse(dao.passwordMatch("password", "pass"));
		assertFalse(dao.passwordMatch(null, "password"));
	}
	
	@Test
	public void loginUserTest() {
		assertTrue(dao.loginUser(user1.getEmail(), "password"));
		assertFalse(dao.loginUser(user1.getEmail(), "incorrect"));
	}
	
	@Test
	public void updateUserTest() {
		assertTrue(dao.updateUser(user2));
	}
	
	@Test
	public void deleteUserTest() {
		assertTrue(dao.deleteUser(user1.getEmail()));
		assertFalse(dao.deleteUser(user1.getEmail()));
	}
	
}
