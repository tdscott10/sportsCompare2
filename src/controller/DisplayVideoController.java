/**
 * Controller for the QuickCompare FXML page
 * 
 * @author Paul Soiya II (psoiya@asu.edu)
 * @version Feb 27, 2015
 */
package controller;

import static view.MainNavigator.COMPARE_FXML;
import static view.MainNavigator.HOME_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import service.DaoFactory;
import service.CompareDao;
import service.ProInfoDao;
import model.ProInfo;
import session.UserSession;
import view.MainNavigator;

public class DisplayVideoController implements Initializable {
	
	view.MainNavigator controller;
    
    @FXML
    private WebView youtubeView;
	
	public DisplayVideoController() {
		controller = new view.MainNavigator();
	}
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    CompareDao compareDao = daoFact.getCompareDao();
    ProInfoDao proInfoDao = daoFact.getProInfoDao();
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        int proStat = compareDao.findStatsId(session.getGameId());
        ProInfo player = proInfoDao.findProInfo(proStat);
        String embed ="https://www.youtube.com/embed/?listType=search&list=" + player.getName() +
        "highlights";
        youtubeView.getEngine().load(embed);
	}
	
	/**
	 * Changes the current FXML page to the home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome(ActionEvent e) {
        youtubeView.getEngine().load(null);
		MainNavigator.loadScreen(HOME_FXML);
	}
	
	/**
	 * Changes the current FXML page to the compare.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToCompare(ActionEvent e) {
        youtubeView.getEngine().load(null);
		MainNavigator.loadScreen(COMPARE_FXML);
	}
}
