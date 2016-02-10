package tests;

import static org.junit.Assert.assertTrue;
import model.Game;
import model.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import service.AbstractDaoFactory;
import service.DaoFactory;
import service.GameDao;
import service.UserDao;

public class GameDaoTest {
	private AbstractDaoFactory daoFact;
	private GameDao dao;
	private UserDao usrDao;
	private Game game1;
	private Game game2;
	private User user;
	private User currentUser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		daoFact = DaoFactory.getDaoFactory();
		
		this.user = new User("testemail5@asu.edu", "password", "TestFname", "TestLname",
				"");
		
		this.dao = daoFact.getGameDao();
		this.usrDao = daoFact.getUserDao();
		usrDao.createUser(user);
		currentUser = usrDao.findUser(user.getEmail());
		this.game1 = new Game(1, currentUser.getId(), 1, "Pittsford", "20-7", "");
		this.game2 = new Game(2, currentUser.getId(), 2, "Rush", "28-7", "");
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateGame() {
		assertTrue(dao.addGame(game1));
	}
	
	@Test
	public void testDeleteGame() {
		assertTrue(dao.deleteGame(game1.getGameID()));
		usrDao.deleteUser(currentUser.getEmail());
	}
	
}
