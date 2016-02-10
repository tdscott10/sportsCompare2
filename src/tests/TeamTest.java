package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import service.*;

public class TeamTest {
    AbstractDaoFactory daoFact = new DaoFactory();
    TeamDao dao = daoFact.getTeamDao();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
    
    @Test
    public void findAllTest() {
        assertTrue(dao.findAll() != null);
    }
    
    @Test
    public void findTeamInfoTest() {
        assertTrue(dao.findTeamInfo("BUF") != null);
    }
}
