package service;

import model.Sport;

public interface SportDao {
	
	/**
	 * Creates a new sport in the Sport table
	 * 
	 * @param sport
	 * @return the auto increment id of the new sport
	 */
	public boolean createSport(Sport sport);
	
	/**
	 * Deletes a sport specified by name
	 * 
	 * @param sportName
	 * @return
	 */
	public boolean deleteSport(String sportName);
	
	/**
	 * Find Position for Football by userId
	 * 
	 * @param userId
	 * @return String position
	 */
	public String findPositionFootball(int userId);
	
	/**
	 * Find Favorite team by userId
	 * 
	 * @param userId
	 * @return String favTeam
	 */
	public String findTeamFootball(int userId);
	
	/**
	 * Find if user has a valid sport
	 * 
	 * @param userId
	 * @return true if sport found
	 */
	public boolean validSport(int userId);
	
}
