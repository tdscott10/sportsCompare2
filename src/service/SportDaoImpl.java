package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Sport;

public class SportDaoImpl implements SportDao {
	
	@Override
	public boolean createSport(Sport sport) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		boolean result = false;
		try {
            String sql = "INSERT OR REPLACE INTO Sport (UserID, SportName, Position, " +
            "FavoriteTeam) VALUES (?,?,?,?);";
			
			stmt = con.prepareStatement(sql);
            stmt.setInt(1, sport.getUserId());
            stmt.setString(2, sport.getName());
			stmt.setString(3, sport.getPosition());
			stmt.setString(4, sport.getFavoriteTeam());

			stmt.execute();
			result = true;
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
	
	@Override
	public boolean deleteSport(String sportName) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			String sql = "DELETE FROM Sport " + "WHERE SportName = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, sportName);
			stmt.execute();
			result = true;
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
	
	@Override
	public String findPositionFootball(int userId) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		String result = "";
		try {
			String sql = "SELECT Position FROM Sport "
					+ "WHERE UserID = ? AND SportName = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setString(2, "Football");
			resultSet = stmt.executeQuery();
			if (resultSet.next())
				result = resultSet.getString("Position");
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
	
	@Override
	public String findTeamFootball(int userId) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		String result = "";
		try {
			String sql = "SELECT FavoriteTeam FROM Sport "
					+ "WHERE UserID = ? AND SportName = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setString(2, "Football");
			resultSet = stmt.executeQuery();
			if (resultSet.next())
				result = resultSet.getString("FavoriteTeam");
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
	
	@Override
	public boolean validSport(int userId) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		boolean result = false;
		try {
			String sql = "SELECT SportName FROM Sport " + "WHERE UserID = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			resultSet = stmt.executeQuery();
			if (resultSet.next())
				result = true;
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
