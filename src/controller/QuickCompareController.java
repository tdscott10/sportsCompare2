/**
 * Controller for the QuickCompare FXML page
 * 
 * @author Paul Soiya II (psoiya@asu.edu)
 * @version Feb 27, 2015
 */
package controller;

import static view.MainNavigator.LOGIN_FXML;
import static view.MainNavigator.QCOMPARESCREEN_FXML;
import static view.MainNavigator.REG_FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Team;
import service.CompareDao;
import service.DaoFactory;
import service.TeamDao;
import session.UserSession;
import view.MainNavigator;

public class QuickCompareController implements Initializable {
	
	view.MainNavigator controller;
    
    @FXML
    private ChoiceBox<String> sportCB;
    @FXML
    private ChoiceBox<String> positionCB;
    @FXML
    private ChoiceBox<String> favTeamCB;
    @FXML
    private TextField attTxt;
    @FXML
    private TextField ydsTxt;
    @FXML
    private TextField touchdownTxt;
    @FXML
    private Label errorLabel;
	
	public QuickCompareController() {
		controller = new view.MainNavigator();
	}
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    CompareDao compareDao = daoFact.getCompareDao();
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        sportCB.setItems(FXCollections.observableArrayList("Football"));
        positionCB.setItems(FXCollections.observableArrayList("QB", "RB", "WR"));
        
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
	 * Changes the current FXML page to the register.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToRegister(ActionEvent e) {
		MainNavigator.loadScreen(REG_FXML);
	}
	
	/**
	 * Changes the current FXML page to the login.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToLogin(ActionEvent e) {
		MainNavigator.loadScreen(LOGIN_FXML);
	}
	
	/**
	 * Changes the current FXML page to the quickCompare.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToQuickCompare(ActionEvent e) {
        if(isInputValid()) {
            int att = Integer.parseInt(attTxt.getText());
            int yds = Integer.parseInt(ydsTxt.getText());
            int tds = Integer.parseInt(touchdownTxt.getText());
            float average = (att/1000)+(yds/100)+(tds*10);
            int proStatsId = compareDao.playerComparison(average, positionCB.getValue(),
                                                     favTeamCB.getValue());
            session.setUserYds(yds);
            session.setUserAtt(att);
            session.setUserTds(tds);
            session.setProStatsId(proStatsId);
            MainNavigator.loadScreen(QCOMPARESCREEN_FXML);
        }
	}
    
    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (positionCB.getValue() == null) {
            errorMessage = "No position choosen";
        }
        
        if (favTeamCB.getValue() == null) {
            errorMessage = "No favorite team choosen";
        }
        
        if (sportCB.getValue() == null) {
            errorMessage = "No sport choosen";
        }
        
        if (attTxt.getText() == null || attTxt.getText().length() == 0) {
            errorMessage = "No Attempts inputted";
        } else {
            try {
                int att = Integer.parseInt(attTxt.getText());
                if(att < 0 || att > 60)
                    errorMessage = "Attempts are not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Attempts input";
            }
        }
        
        if (ydsTxt.getText() == null || ydsTxt.getText().length() == 0) {
            errorMessage = "No Yards inputted";
        } else {
            try {
                int yds = Integer.parseInt(ydsTxt.getText());
                if(yds < 0 || yds > 600)
                    errorMessage = "Yards are not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Yards input";
            }
        }
        
        if (touchdownTxt.getText() == null || touchdownTxt.getText().length() == 0) {
            errorMessage = "No Touchdowns inputted";
        } else {
            try {
                int tds = Integer.parseInt(touchdownTxt.getText());
                if(tds < 0 || tds > 8)
                    errorMessage = "Touchdowns are not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid touchdown input";
            }
        }
        errorLabel.setText(errorMessage);
        return errorMessage.length() == 0;
    }
}
