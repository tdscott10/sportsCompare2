package service;

import java.util.List;

import model.Team;
import model.TeamInfo;

public interface TeamDao {
	
	/**
	 * Retrieves all teams from from the professional db team table
	 * 
	 * @param user
	 * @return the auto increment id of the new user
	 */
	public List<Team> findAll();
    
    /**
     * Find Team info
     * @param teamId
     * @return TeamInfo
     */
    public TeamInfo findTeamInfo(String teamId);
}
