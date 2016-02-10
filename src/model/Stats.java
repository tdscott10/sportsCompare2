/**
 * Stats
 *
 * @author John Olin
 * @version 
 */
package model;

public class Stats {
	
	private int statsID;
	private int gameID;
	private int userID;
	private int yards;
	private int touchdowns;
	private int attempts;
	private float average;
	
	public Stats() {
	}
	
	public Stats(int gameID, int userID, int yards, int touchdowns, int attempts) {
		this.gameID = gameID;
		this.userID = userID;
		this.yards = yards;
		this.touchdowns = touchdowns;
		this.attempts = attempts;
		average = compareAlgorithm(yards, touchdowns, attempts);
	}
	
	public int getStatsID() {
		return statsID;
	}
	
	public void setStatsID(int statsID) {
		this.statsID = statsID;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getYards() {
		return yards;
	}
	
	public void setYards(int yards) {
		this.yards = yards;
	}
	
	public int getTouchdowns() {
		return touchdowns;
	}
	
	public void setTouchdowns(int touchdowns) {
		this.touchdowns = touchdowns;
	}
	
	public int getAttempts() {
		return attempts;
	}
	
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
	public float getAverage() {
		return average;
	}
	
	public void setAverage() {
		this.average = compareAlgorithm(yards, touchdowns, attempts);
	}
	
	static float compareAlgorithm(int yds, int tds, int att) {
		float actualTDs = (float) tds * 10;
		float actualYds = (float) yds / 100;
		float actualAtt = (float) att / 1000;
		float weightedAverage = actualTDs + actualYds + actualAtt;
		
		return weightedAverage;
	}
	
}
