/**
 * Controller for the Game FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 16, 2015
 */
package controller;

import static view.MainNavigator.COMPARE_FXML;
import static view.MainNavigator.HOME_FXML;
import static view.MainNavigator.STATS_FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Game;
import service.DaoFactory;
import service.GameDao;
import session.UserSession;
import view.MainNavigator;

public class GameController implements Initializable {
	
	view.MainNavigator controller;
	
	@FXML
	private TableColumn<Game, Integer> weekCol;
	@FXML
	private TableColumn<Game, String> dateCol;
	@FXML
	private TableColumn<Game, String> opponentCol;
	@FXML
	private TableColumn<Game, String> scoreCol;
	@FXML
	private TableView<Game> table;
	
	private UserSession session = UserSession.getInstance();
	
	DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
	GameDao gameDao = daoFact.getGameDao();
	
	List<Game> games = new ArrayList<Game>();
	
	public GameController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		games = gameDao.findGames(session.getUserId());
		weekCol.setCellValueFactory(new PropertyValueFactory<Game, Integer>("week"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Game, String>("date"));
		opponentCol
				.setCellValueFactory(new PropertyValueFactory<Game, String>("opponent"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<Game, String>("score"));
		ObservableList<Game> data = FXCollections.observableArrayList();
		
		for (int i = 0; i < games.size(); i++) {
			data.add(games.get(i));
		}
		
		table.setItems(data);
		
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					Node node = ((Node) event.getTarget()).getParent();
					TableRow<Game> row;
					if (node instanceof TableRow) {
						row = (TableRow<Game>) node;
					}
					else {
						row = (TableRow<Game>) node.getParent();
					}
					Game game = new Game();
					game = (Game) row.getItem();
					
					if (game != null) {
						game.setUserID(session.getUserId());
						int gameId = gameDao.findGameID(game);
						session.setGameId(gameId);
						MainNavigator.loadScreen(COMPARE_FXML);
					}
				}
			}
		});
	}
	
	/**
	 * Changes the current FXML page to stats.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToStats() {
		MainNavigator.loadScreen(STATS_FXML);
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
}
