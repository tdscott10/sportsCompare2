/**
 * StatsDao Implementation
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Stats;

public class StatsDaoImpl implements StatsDao {
	
	@Override
	public boolean addStats(Stats stats) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		boolean result = true;
		try {
			String sql = "INSERT INTO Stats (GameID, UserID, "
					+ "Yds, Tds, Att, Average) " + "VALUES(?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, stats.getGameID());
			stmt.setInt(2, stats.getUserID());
			stmt.setInt(3, stats.getYards());
			stmt.setInt(4, stats.getTouchdowns());
			stmt.setInt(5, stats.getAttempts());
			stmt.setFloat(6, stats.getAverage());
			stmt.execute();
		}
		catch (Exception e) {
			result = false;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		finally {
			try {
				if (stmt != null) {
					stmt.close();
					DaoFactory.closeConnection(con);
				}
			}
			catch (Exception e) {
				result = false;
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		return result;
	}
	
	@Override
	public float findCompareAverage(int gameID) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		float result = -1.0f;
		try {
			String sql = "SELECT Average FROM Stats " + "WHERE GameID = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, gameID);
			resultSet = stmt.executeQuery();
			if (resultSet.next())
				result = resultSet.getFloat("Average");
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		finally {
			try {
				if (stmt != null) {
					stmt.close();
					DaoFactory.closeConnection(con);
				}
			}
			catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		return result;
	}
}
