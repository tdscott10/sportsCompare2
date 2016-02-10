package model;

public class Sport {
	private int sportId;
	private int userId;
	private String name;
	private String position;
	private String favoriteTeam;
	
	public Sport(int sportId, int userId, String name, String position,
			String favoriteTeam) {
		this.sportId = sportId;
		this.userId = userId;
		this.name = name;
		this.position = position;
		this.favoriteTeam = favoriteTeam;
		
	}
	
	public Sport(int userId, String name, String position, String favoriteTeam) {
		this.userId = userId;
		this.name = name;
		this.position = position;
		this.favoriteTeam = favoriteTeam;
	}
	
	public int getSportId() {
		return sportId;
	}
	
	public void setSportId(int sportId) {
		this.sportId = sportId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getFavoriteTeam() {
		return favoriteTeam;
	}
	
	public void setFavoriteTeam(String favoriteTeam) {
		this.favoriteTeam = favoriteTeam;
	}
	
}
