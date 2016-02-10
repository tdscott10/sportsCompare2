/**
 * TeamInfo
 *
 * @author John Olin
 * @version 
 */
package model;

public class TeamInfo {
	
    private String teamId;
    private String teamName;
    private String teamPicture;
    private String primaryColor;
    private String secondaryColor;
	
	public TeamInfo() {
	}
	
	public TeamInfo(String teamId, String teamName, String teamPicture, String primaryColor,
                   String secondaryColor) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamPicture = teamPicture;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
	
	public String getTeamId() {
		return teamId;
	}
	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getTeamPicture() {
        return teamPicture;
    }
    
    public void setTeamPicture(String teamPicture) {
        this.teamPicture = teamPicture;
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
