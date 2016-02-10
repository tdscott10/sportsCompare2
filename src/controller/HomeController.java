/**
 * Controller for the Home page FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 16, 2015
 */
package controller;

import static view.MainNavigator.EDIT_FXML;
import static view.MainNavigator.GAME_FXML;
import static view.MainNavigator.SPORT_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import service.DaoFactory;
import service.SportDao;
import session.UserSession;
import view.MainNavigator;

public class HomeController implements Initializable {
	
	view.MainNavigator controller;
	
	private UserSession session = UserSession.getInstance();
	
	@FXML
	private Button footballButton;
	@FXML
	private Button recentGamesButton;
	
	DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
	SportDao sportDao = daoFact.getSportDao();
	
	public HomeController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boolean validSport = sportDao.validSport(session.getUserId());
		footballButton.setVisible(validSport);
		recentGamesButton.setVisible(validSport);
	}
	
	/**
	 * Changes the current FXML page to games.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToGames(ActionEvent e) {
		MainNavigator.loadScreen(GAME_FXML);
	}
	
	/**
	 * Changes the current FXML page to sport.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToSport(ActionEvent e) {
		MainNavigator.loadScreen(SPORT_FXML);
	}
	
	/**
	 * Changes the current FXML page to edit.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToEdit(ActionEvent e) {
		MainNavigator.loadScreen(EDIT_FXML);
	}
}
