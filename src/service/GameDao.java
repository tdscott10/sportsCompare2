/**
 * GameDao
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 26, 2015
 */
package service;

import java.util.List;

import model.Game;

public interface GameDao {
	
	/**
	 * Creates a new game in the Game Table
	 * 
	 * @param game
	 * @return true if successfully entered
	 */
	public boolean addGame(Game game);
	
	/**
	 * Deletes a game in the Game Table
	 * 
	 * @param gameID
	 * @return true if successfully deleted
	 */
	public boolean deleteGame(int gameId);
	
	/**
	 * Finds all Games in the Game Table for a User
	 * 
	 * @param userId
	 * @return array of games for the user
	 */
	public List<Game> findGames(int userId);
	
	/**
	 * Finds gameId for a Game
	 * 
	 * @param game
	 * @return gameId
	 */
	public int findGameID(Game game);
}
