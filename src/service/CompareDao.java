/**
 * CompareDao
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package service;

import model.Compare;

public interface CompareDao {
    /**
     * Pulls down stats for both the User and Pro
     * @param average, position, favTeam
     * @return int of StatsID for pro player
     */
    public int playerComparison(float average, String position, String favTeam);
    
    /**
     * Inserts proStatsId into individual db
     * @param proStatsId, gameId
     * @return true if sucessful
     */
    public boolean insertStat(int proStatsId, int gameId);
    
    /**
     * Returns Position and Favorite Team for user
     * @param userId
     * @return String array of Position FavoriteTeam
     */
    public String[] userPositionTeam(int userId);
    
    /**
     * Finds statsID given a gameID
     * @param gameId
     * @return int StatsID
     */
    public int findStatsId(int gameId);
    
    /**
     * Creates a compare object
     * @param gameId, statsId
     * @return Compare
     */
    public Compare inputStats(int gameId, int statsId);
    
    /**
     * Creates a compare with just pro stats
     * @param statsId
     * @return Compare
     */
    public Compare qcInputStats(int statsId);
}
