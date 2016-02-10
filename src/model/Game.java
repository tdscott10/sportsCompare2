package model;

public class Game {
	
	private int gameID;
	private int userID;
	private int week;
	private String opponent;
	private String score;
	private String date;
	
	public Game() {
	}
	
	public Game(int gameID, int userID, int week, String opponent, String score,
			String date) {
		this.gameID = gameID;
		this.userID = userID;
		this.week = week;
		this.opponent = opponent;
		this.score = score;
		this.date = date;
	}
	
	public Game(int userID, int week, String opponent, String score, String date) {
		this.userID = userID;
		this.week = week;
		this.opponent = opponent;
		this.score = score;
		this.date = date;
	}
	
	public Game(int week, String date, String opponent, String score) {
		this.week = week;
		this.date = date;
		this.opponent = opponent;
		this.score = score;
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
	
	public int getWeek() {
		return week;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public String getOpponent() {
		return opponent;
	}
	
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
}
