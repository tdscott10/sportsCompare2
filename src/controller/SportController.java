/**
 * Controller for the Sport FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 16, 2015
 */
package controller;

import static view.MainNavigator.HOME_FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Sport;
import model.Team;
import service.DaoFactory;
import service.SportDao;
import service.TeamDao;
import view.MainNavigator;

public class SportController implements Initializable {
	
	MainNavigator controller;
	
	@FXML
	private ChoiceBox<String> sportCB;
	@FXML
	private ChoiceBox<String> positionCB;
	@FXML
	private ChoiceBox<String> favTeamCB;
    @FXML
    private Label errorLabel;
	
	public SportController() {
		controller = new view.MainNavigator();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		sportCB.setItems(FXCollections.observableArrayList("Football"));
		positionCB.setItems(FXCollections.observableArrayList("QB", "RB", "WR"));
		
		DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
		TeamDao teamDao = daoFact.getTeamDao();
		
		List<Team> teams = teamDao.findAll();
		List<String> teamNames = new ArrayList<String>();
		
		//iterate through each team and extract the team name
		for (Team team : teams) {
			teamNames.add(team.getTeamName());
		}
		
		favTeamCB.setItems(FXCollections.observableArrayList(teamNames));
		
	}
	
	/**
	 * Changes the current FXML page to home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome() {
		
		DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
		SportDao sportDao = daoFact.getSportDao();

        Sport sport = new Sport(controller.getSessionUserId(), sportCB.getValue(),
				positionCB.getValue(), favTeamCB.getValue());
        
        if(sportCB.getValue() != null && positionCB.getValue() != null &&
           favTeamCB.getValue() != null) {
            if (sportDao.createSport(sport))
                MainNavigator.loadScreen(HOME_FXML);
        } else
            errorLabel.setText("Please Enter All Selections");
	}
    
    /**
     * Changes the current FXML page to home.fxml
     *
     * @param e
     */
    @FXML
    private void changeToHomeQuick() {
        MainNavigator.loadScreen(HOME_FXML);
    }
}
