package service;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Dao factory for the SQLite database
 * 
 * @author Paul Soiya II
 * @version March 17, 2015
 */
public class DaoFactory extends AbstractDaoFactory {
	
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String CON_URL_IND = "jdbc:sqlite:individual.db";
	public static final String CON_URL_PRO = "jdbc:sqlite:professional.db";
	
	public static Connection createConnection(String database) {
		Connection con = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(database);
			
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return con;
	}
	
	public static Connection createConnectionIndividual() {
		Connection con = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(DaoFactory.CON_URL_IND);
			
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return con;
	}
	
	public static Connection createConnectionProfessional() {
		Connection con = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(DaoFactory.CON_URL_PRO);
			
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return con;
	}
	
	public static void closeConnection(Connection con) {
		try {
			con.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	@Override
	public UserDao getUserDao() {
		return new UserDaoImpl();
	}
	
	@Override
	public GameDao getGameDao() {
		return new GameDaoImpl();
	}
	
	@Override
	public SportDao getSportDao() {
		return new SportDaoImpl();
	}
	
	@Override
	public StatsDao getStatsDao() {
		return new StatsDaoImpl();
	}
	
	@Override
	public CompareDao getCompareDao() {
		return new CompareDaoImpl();
	}

	@Override
	public TeamDao getTeamDao() {
		return new TeamDaoImpl();
	}
    
    @Override
    public ProInfoDao getProInfoDao() {
        return new ProInfoDaoImpl();
    }
	
}
