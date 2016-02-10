package tests;

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

public class EditUserDaoTest {
	private UserDao dao;
	private User user1;
	private User user2;
	
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
				"3-8-15");
		this.user2 = new User("testemail2@asu.edu", "password", "TestFname", "TestLname",
				"3-8-15");
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void createUserTest() {
		assertTrue(dao.createUser(user1));
	}
	
	@Test
	public void editUserAccount() {
		
	}
	
}
