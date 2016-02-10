/**
 * Controller for the QuickCompare FXML page
 * 
 * @author Paul Soiya II (psoiya@asu.edu)
 * @version Feb 27, 2015
 */
package controller;

import static view.MainNavigator.QCOMPARESCREEN_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import service.DaoFactory;
import service.ProInfoDao;
import model.ProInfo;
import session.UserSession;
import view.MainNavigator;

public class QCDisplayVideoController implements Initializable {
	
	view.MainNavigator controller;
    
    @FXML
    private WebView youtubeView;
	
	public QCDisplayVideoController() {
		controller = new view.MainNavigator();
	}
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    ProInfoDao proInfoDao = daoFact.getProInfoDao();
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        ProInfo player = proInfoDao.findProInfo(session.getProStatsId());
        String embed ="https://www.youtube.com/embed/?listType=search&list=" + player.getName() +
        "highlights";
        youtubeView.getEngine().load(embed);
	}
	
	/**
	 * Changes the current FXML page to the compare.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToCompare(ActionEvent e) {
        youtubeView.getEngine().load(null);
		MainNavigator.loadScreen(QCOMPARESCREEN_FXML);
	}
}
