/**
 * GameDao Implementation
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 26, 2015
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Game;

public class GameDaoImpl implements GameDao {
	
	@Override
	public boolean addGame(Game game) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		boolean result = true;
		try {
			String sql = "INSERT INTO GameLog (UserID, Week, Date, Opponent, Score)"
					+ "VALUES(?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, game.getUserID());
			stmt.setInt(2, game.getWeek());
			stmt.setString(3, game.getDate());
			stmt.setString(4, game.getOpponent());
			stmt.setString(5, game.getScore());
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
	public boolean deleteGame(int gameId) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			String sql = "DELETE FROM GameLog " + "WHERE GameID = ?";
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, gameId);
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
	public List<Game> findGames(int userId) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		List<Game> games = null;
		int length = 0;
		try {
			// TODO: refactor - 3 blocks with varied objectives
            String sql = "SELECT COUNT(UserID) AS total FROM GameLog WHERE UserID = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();
            if (resultSet.next())
                length = resultSet.getInt("total");
            stmt.close();
            
            games = new ArrayList<Game>();
            
			sql = "SELECT * FROM GameLog WHERE UserID = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			resultSet = stmt.executeQuery();
			
			List<Integer> week = new ArrayList<Integer>();
			List<String> opp = new ArrayList<String>();
			List<String> score = new ArrayList<String>();
			List<String> date = new ArrayList<String>();
			
			for (int i = 0; i < length; i++) {
				while (resultSet.next()) {
					week.add(resultSet.getInt("Week"));
					date.add(resultSet.getString("Date"));
					opp.add(resultSet.getString("Opponent"));
					score.add(resultSet.getString("Score"));
				}
				games.add(new Game(week.get(i), date.get(i), opp.get(i), score.get(i)));
			}
			
		}
		catch (Exception exc) {
			System.err.println(exc.getClass().getName() + ":" + exc.getMessage());
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
		return games;
	}
	
	@Override
	public int findGameID(Game game) {
		Connection con = DaoFactory.createConnectionIndividual();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		int result = -1;
		try {
			String sql = "SELECT GameID FROM GameLog WHERE UserID = ? "
					+ "AND Week = ? AND Opponent = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, game.getUserID());
			stmt.setInt(2, game.getWeek());
			stmt.setString(3, game.getOpponent());
			resultSet = stmt.executeQuery();
			if (resultSet.next())
				result = resultSet.getInt("GameID");
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
