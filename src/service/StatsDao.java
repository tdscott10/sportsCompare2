/**
 * StatsDao
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package service;

import model.Stats;

public interface StatsDao {
	
	/**
	 * Creates new stats in the Stats Table
	 * 
	 * @param stats
	 * @return true if successfully entered
	 */
	public boolean addStats(Stats stats);
	
	/**
	 * Finds Average of users stats for a game
	 * 
	 * @param gameId
	 * @return float of average value
	 */
	public float findCompareAverage(int gameID);
}
