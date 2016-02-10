package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import model.Game;
import model.Stats;
import service.*;

public class StatsTest {
    AbstractDaoFactory daoFact = new DaoFactory();
    StatsDao dao = daoFact.getStatsDao();
    GameDao gDao = daoFact.getGameDao();
    Stats stats = new Stats(1,1,100,2,20);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		Game game = new Game(1,1, "Bulldogs", "17-10", "2015-4-4");
		gDao.addGame(game);
	}

	@After
	public void tearDown() throws Exception {
	}
    
    @Test
    public void addStatsTest() {
        assertTrue(dao.addStats(stats));
    }
    
    @Test
    public void findProInfoTest() {
    	
        assertTrue(dao.findCompareAverage(1) > 0.0f);
    }
}
