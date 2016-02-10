/**
 * Compare
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version 
 */
package model;

public class Compare {
	
	private int userYards;
	private int userTouchdowns;
	private int userAttempts;
	private int proYards;
	private int proTouchdowns;
	private int proAttempts;
	private String proGameId;
	private String gameDate;
	private String gameTeam2;
	private String gameScore;
	private String gameTeam1;
	
	public Compare() {
	}
	
	public int getUserYards() {
		return userYards;
	}
	
	public void setUserYards(int userYards) {
		this.userYards = userYards;
	}
	
	public int getUserTouchdowns() {
		return userTouchdowns;
	}
	
	public void setUserTouchdowns(int userTouchdowns) {
		this.userTouchdowns = userTouchdowns;
	}
	
	public int getUserAttempts() {
		return userAttempts;
	}
	
	public void setUserAttempts(int userAttempts) {
		this.userAttempts = userAttempts;
	}
	
	public int getProYards() {
		return proYards;
	}
	
	public void setProYards(int proYards) {
		this.proYards = proYards;
	}
	
	public int getProTouchdowns() {
		return proTouchdowns;
	}
	
	public void setProTouchdowns(int proTouchdowns) {
		this.proTouchdowns = proTouchdowns;
	}
	
	public int getProAttempts() {
		return proAttempts;
	}
	
	public void setProAttempts(int proAttempts) {
		this.proAttempts = proAttempts;
	}
	
	public String getProGameId(){
		return proGameId;
	}

	public void setProGameId(String proGameId) {
		this.proGameId = proGameId;	
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;	
	}
	
	public String getGameDate() {
		return gameDate;
	}
	
	public void setGameTeam1(String gameTeam1) {
		this.gameTeam1 = gameTeam1;	
	}
	
	public String getGameTeam1(){
		return gameTeam1;
	}

	public void setGameTeam2(String gameTeam2) {
		this.gameTeam2 = gameTeam2;
	}
	
	public String getGameTeam2() {
		return gameTeam2;
	}

	public void setGameScore(String gameScore) {
		this.gameScore= gameScore;	
	}

	public String getGameScore() {
		return gameScore;
	}

	
}
