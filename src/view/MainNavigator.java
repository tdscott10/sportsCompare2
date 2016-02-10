package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import session.UserSession;
import controller.LoginController;

/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate simple access from anywhere in the application.
 */
public class MainNavigator {
	
	/**
	 * Convenience constants for fxml layouts managed by the navigator.
	 */
	public static final String REG_FXML = "register.fxml";
	public static final String LOGIN_FXML = "login.fxml";
	public static final String HOME_FXML = "home.fxml";
	public static final String STATS_FXML = "stats.fxml";
	public static final String SPORT_FXML = "sport.fxml";
	public static final String GAME_FXML = "games.fxml";
	public static final String EDIT_FXML = "edit.fxml";
	public static final String QCOMPARE_FXML = "quickCompare.fxml";
    public static final String QCOMPARESCREEN_FXML = "quickCompareDisplay.fxml";
	public static final String COMPARE_FXML = "compare.fxml";
    public static final String PASSWORD_FXML = "password.fxml";
    public static final String SHARE_STAT_FXML = "shareStat.fxml";
    public static final String DISPLAY_VIDEO_FXML = "displayVideo.fxml";
    public static final String QCDISPLAY_VIDEO_FXML = "qcDisplayVideo.fxml";
    public static final String GRAPH_FXML = "compareGraph.fxml";
    public static final String QC_GRAPH_FXML = "qcCompareGraph.fxml";
    
	/** The main application layout controller. */
	private static LoginController mainController;
	private UserSession session;
	
	public MainNavigator() {
		super();
		session = UserSession.getInstance();
	}
	
	/**
	 * Stores the main controller for later use in navigation tasks.
	 *
	 * @param mainController
	 *            the main application layout controller.
	 */
	public static void setMainController(LoginController mainController) {
		MainNavigator.mainController = mainController;
	}
	
	/**
	 * Loads the vista specified by the fxml file into the vistaHolder pane of the main application layout.
	 *
	 * Previously loaded vista for the same fxml file are not cached. The fxml is loaded anew and a new vista node
	 * hierarchy generated every time this method is invoked.
	 *
	 * A more sophisticated load function could potentially add some enhancements or optimizations, for example: cache
	 * FXMLLoaders cache loaded vista nodes, so they can be recalled or reused allow a user to specify vista node reuse
	 * or new creation allow back and forward history like a browser
	 *
	 * @param fxml
	 *            the fxml file to be loaded.
	 */
	public static void loadScreen(String fxml) {
		try {
			mainController.setScreen(FXMLLoader.load(MainNavigator.class
					.getResource(fxml)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public UserSession getSession() {
		return session;
	}
	
	public void setSessionUserId(int userId) {
		this.session.setUserId(userId);
	}
	
	public int getSessionUserId() {
		return this.session.getUserId();
	}
	
	public void setSessionUserEmail(String userEmail) {
		this.session.setUserEmail(userEmail);
	}
	
	public String getSessionUserEmail(String userEmail) {
		return this.session.getUserEmail();
	}
	
	public boolean isSessionValid() {
		return this.session.isSessionValid();
	}
	
}
