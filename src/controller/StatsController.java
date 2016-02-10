/**
 * Controller for the Stats FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package controller;

import static view.MainNavigator.COMPARE_FXML;
import static view.MainNavigator.GAME_FXML;
import static view.MainNavigator.HOME_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.Game;
import model.Stats;
import service.CompareDao;
import service.DaoFactory;
import service.GameDao;
import service.SportDao;
import service.StatsDao;
import session.UserSession;
import view.MainNavigator;

public class StatsController implements Initializable {
	
	view.MainNavigator controller;
	
	@FXML
	private TextField weekTxt;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField opponentTxt;
	@FXML
	private TextField yourScore;
	@FXML
	private TextField theirScore;
	@FXML
	private TextField attemptsTxt;
	@FXML
	private TextField yardsTxt;
	@FXML
	private TextField touchdownTxt;
    @FXML
    private Label errorLabel;
    
    private UserSession session = UserSession.getInstance();
	
	public StatsController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Changes the current FXML page to compare.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToCompare() {
		DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
		GameDao gameDao = daoFact.getGameDao();
		StatsDao statsDao = daoFact.getStatsDao();
		SportDao sportDao = daoFact.getSportDao();
		CompareDao compareDao = daoFact.getCompareDao();
		try {
			// Load Game info
			if(isInputValid());
			
			int week = Integer.parseInt(weekTxt.getText());
			Game game = new Game(controller.getSessionUserId(), week,
					opponentTxt.getText(), yourScore.getText() + "-"
							+ theirScore.getText(), datePicker.getValue().toString());
			gameDao.addGame(game);
			
			// Load Stats
			int gameId = gameDao.findGameID(game);
            session.setGameId(gameId);
			int yards = Integer.parseInt(yardsTxt.getText());
			int touchdown = Integer.parseInt(touchdownTxt.getText());
			int attempts = Integer.parseInt(attemptsTxt.getText());
			Stats stats = new Stats(gameId, controller.getSessionUserId(), yards,
					touchdown, attempts);
			
            String position = sportDao.findPositionFootball(controller.getSessionUserId());
            String team = sportDao.findTeamFootball(controller.getSessionUserId());
            statsDao.addStats(stats);
            int proStat = compareDao.playerComparison(statsDao.findCompareAverage(gameId),
                                                      position, team);
            compareDao.insertStat(proStat, gameId);
            MainNavigator.loadScreen(COMPARE_FXML);
            
   
		}
		catch (NumberFormatException e) {
			System.out.println(this.getClass().getName() + " error: " + e.getMessage());
		}
	}
	
	/**
	 * Changes the current FXML page to home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome() {
		MainNavigator.loadScreen(HOME_FXML);
	}
    
    /**
     * Changes the current FXML page to games.fxml
     *
     * @param e
     */
    @FXML
    private void changeToGames() {
        MainNavigator.loadScreen(GAME_FXML);
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (weekTxt.getText() == null || weekTxt.getText().length() == 0) {
            errorMessage = "No Week inputted";
        } else {
            try {
                int week = Integer.parseInt(weekTxt.getText());
                if(week < 0 || week > 40)
                    errorMessage = "Week is not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Week input";
            }
        }
        
        if (datePicker.getValue() == null || opponentTxt.getText().length() == 0) {
            errorMessage = "No date choosen";
        }
        
        
        if (opponentTxt.getText() == null || opponentTxt.getText().length() == 0) {
            errorMessage = "No opponent input";
        }
        
        if (yourScore.getText() == null || yourScore.getText().length() == 0) {
            errorMessage = "No Your Score inputted";
        } else {
            try {
                int score = Integer.parseInt(yourScore.getText());
                if(score < 0 || score > 100)
                    errorMessage = "Your Score is not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Your Score input";
            }
        }
        
        if (theirScore.getText() == null || theirScore.getText().length() == 0) {
            errorMessage = "No Their Score inputted";
        } else {
            try {
                int score = Integer.parseInt(theirScore.getText());
                if(score < 0 || score > 100)
                    errorMessage = "Their Score is not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Their Score input";
            }
        }
        
        if (attemptsTxt.getText() == null || attemptsTxt.getText().length() == 0) {
            errorMessage = "No Attempts inputted";
        } else {
            try {
                int att = Integer.parseInt(attemptsTxt.getText());
                if(att < 0 || att > 60)
                    errorMessage = "Attempts are not within range";
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Attempts input";
            }
        }
        
        if (yardsTxt.getText() == null || yardsTxt.getText().length() == 0) {
            errorMessage = "No Yards inputted";
        } else {
            try {
                int yds = Integer.parseInt(yardsTxt.getText());
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
