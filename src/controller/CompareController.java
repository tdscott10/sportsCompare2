/**
 * Controller for the Compare FXML page
 * 
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package controller;

import static view.MainNavigator.DISPLAY_VIDEO_FXML;
import static view.MainNavigator.HOME_FXML;
import static view.MainNavigator.SHARE_STAT_FXML;
import static view.MainNavigator.GRAPH_FXML;
import static view.MainNavigator.GAME_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Compare;
import model.ProInfo;
import model.TeamInfo;
import service.CompareDao;
import service.DaoFactory;
import service.ProInfoDao;
import service.TeamDao;
import session.UserSession;
import view.Main;
import view.MainNavigator;

public class CompareController implements Initializable {
	
	@FXML
	private GridPane compGP;
	@FXML
	private Label nameTxt;
	@FXML
	private Label positionTxt;
	@FXML
	private Label numberTxt;
	@FXML
	private Label teamTxt;
	@FXML
	private Label dobTxt;
	@FXML
	private Label weightTxt;
	@FXML
	private Label heightTxt;
	@FXML
	private Label collegeTxt;
	@FXML
	private Label attemptsTxt;
	@FXML
	private Label userAttemptsTxt;
	@FXML
	private Label yardsTxt;
	@FXML
	private Label userYardsTxt;
	@FXML
	private Label touchdownTxt;
	@FXML
	private Label userTouchdownTxt;
	@FXML
	private Label dateTxt;
	@FXML
	private Label gameTeam1Txt;
	@FXML
	private Label gameTeam2Txt;
	@FXML
	private Label scoreTxt;
	@FXML
	private ImageView playerPicture;
	@FXML
	private ImageView teamPicture;
    @FXML
    private Button shareBtn;
    @FXML
    private Button graphBtn;
    @FXML
    private Button videoBtn;
    @FXML
    private HBox topBar;
	
	view.MainNavigator controller;
	
	private UserSession session = UserSession.getInstance();
	
	DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
	ProInfoDao infoDao = daoFact.getProInfoDao();
	CompareDao compareDao = daoFact.getCompareDao();
	TeamDao teamDao = daoFact.getTeamDao();
	
	ProInfo info = new ProInfo();
	TeamInfo teamInfo = new TeamInfo();
	Compare comp = new Compare();
	
	public CompareController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int statsId = compareDao.findStatsId(session.getGameId());
		
		info = infoDao.findProInfo(statsId);
		teamInfo = teamDao.findTeamInfo(info.getTeam());
		comp = compareDao.inputStats(session.getGameId(), statsId);
		nameTxt.setText(info.getName());
		positionTxt.setText(info.getPosition());
		numberTxt.setText(Integer.toString(info.getNumber()));
		collegeTxt.setText(info.getCollege());
		heightTxt.setText(Integer.toString(info.getHeight()));
		weightTxt.setText(Integer.toString(info.getWeight()));
		dobTxt.setText(info.getDOB());
		teamTxt.setText(teamInfo.getTeamName());
		Image img = new Image(info.getPicture());
		playerPicture.setImage(img);
		Image imgTeam = new Image(teamInfo.getTeamPicture());
		teamPicture.setImage(imgTeam);
		attemptsTxt.setText(Integer.toString(comp.getProAttempts()));
		yardsTxt.setText(Integer.toString(comp.getProYards()));
		touchdownTxt.setText(Integer.toString(comp.getProTouchdowns()));
		userAttemptsTxt.setText(Integer.toString(comp.getUserAttempts()));
		userYardsTxt.setText(Integer.toString(comp.getUserYards()));
		userTouchdownTxt.setText(Integer.toString(comp.getUserTouchdowns()));
		String date = comp.getGameDate();
		dateTxt.setText(date.substring(0,10));
		gameTeam1Txt.setText(comp.getGameTeam1());
		gameTeam2Txt.setText(comp.getGameTeam2());
		scoreTxt.setText(comp.getGameScore());
		shareBtn.setStyle("-fx-background-color: " + teamInfo.getPrimaryColor()
				+ "; -fx-text-fill: " + teamInfo.getSecondaryColor() + "; "
				+ "-fx-border-color: " + teamInfo.getSecondaryColor() + ";"
				+ " -fx-border-width: 3px;");
		graphBtn.setStyle("-fx-background-color: " + teamInfo.getPrimaryColor()
				+ "; -fx-text-fill: " + teamInfo.getSecondaryColor() + ";"
				+ "-fx-border-color: " + teamInfo.getSecondaryColor() + ";"
				+ " -fx-border-width: 3px;");
		videoBtn.setStyle("-fx-background-color: " + teamInfo.getPrimaryColor()
				+ "; -fx-text-fill: " + teamInfo.getSecondaryColor() + ";"
				+ "-fx-border-color: " + teamInfo.getSecondaryColor() + ";"
				+ " -fx-border-width: 3px;");
		topBar.setStyle("-fx-background-color: " + teamInfo.getPrimaryColor()
				+ ";");
	}
	
	/**
	 * Changes the current FXML page to the home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome(ActionEvent e) {
		MainNavigator.loadScreen(HOME_FXML);
	}
	
	/**
	 * Changes the current FXML page to the games.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToRecentGames(ActionEvent e) {
		MainNavigator.loadScreen(GAME_FXML);
	}
	
	@FXML
	public void changeToShareStat(ActionEvent e) {
		System.out.println("ABOUT TO SHARE STAT");
		String screenshotPath = Main.takeScreenshot(compGP);
		session.setScreenshotPath(screenshotPath);
		MainNavigator.loadScreen(SHARE_STAT_FXML);
	}
	
	@FXML
	public void changetoVideoDisplay(ActionEvent e) {
		MainNavigator.loadScreen(DISPLAY_VIDEO_FXML);
	}
	
	@FXML
	public void changeToGraph(ActionEvent e) {
		MainNavigator.loadScreen(GRAPH_FXML);
	}
}
