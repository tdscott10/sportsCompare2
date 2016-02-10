package model;

/**
 * Model for professional NFL teams
 * @author John Olin
 * @version 
 */
public class Team {
	
	private String teamID;
	private String teamName;
	private String logo;
	private String primaryColor;
	private String secondaryColor;
	
	
	public Team() { }
	
	public Team(String teamID, String teamName, String logo, 
			String primaryColor, String secondaryColor){
		this.teamID = teamID;
		this.teamName = teamName;
		this.logo = logo;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
	
}
