package session;

/**
 * Used to keep track of the important session related data
 * 
 * @author Paul Soiya II
 * @version March 28, 2015
 */
public final class UserSession {
	
	private static UserSession instance = null;
	private int userId;
	private String userEmail;
    private int gameId;
    private int proStatsId;
    private String screenshotPath;
    private int userYds;
    private int userAtt;
    private int userTds;
	private boolean sessionValid;
	public static final int INVALID_VALUE = -1;
	
	private UserSession() {
		// set the default value of userId to the invalid value
		this.userId = INVALID_VALUE;
		this.userEmail = null;
        this.gameId = INVALID_VALUE;
        this.proStatsId = INVALID_VALUE;
		this.sessionValid = false;
		
	}
	
	public static UserSession getInstance() {
		if (instance == null) {
			instance = new UserSession();
		}
		return instance;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
    
    public int getGameId() {
        return gameId;
    }
    
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public int getProStatsId() {
        return proStatsId;
    }
    
    public void setProStatsId(int proStatsId) {
        this.proStatsId = proStatsId;
    }
	
	public boolean isSessionValid() {
		return sessionValid;
	}
	
	public void setSessionValid(boolean sessionValid) {
		this.sessionValid = sessionValid;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
    
    public int getUserYds() {
        return userYds;
    }
    
    public void setUserYds(int userYds) {
        this.userYds = userYds;
    }
    
    public int getUserAtt() {
        return userAtt;
    }
	
    public void setUserAtt(int userAtt) {
        this.userAtt = userAtt;
    }
	
    public int getUserTds() {
        return userTds;
    }
    
    public void setUserTds(int userTds) {
        this.userTds = userTds;
    }
}
