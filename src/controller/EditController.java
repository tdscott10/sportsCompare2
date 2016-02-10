/**
 * Controller for the Edit account info FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 16, 2015
 */
package controller;

import static view.MainNavigator.GAME_FXML;
import static view.MainNavigator.HOME_FXML;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.ValidateResult;
import service.DaoFactory;
import service.SportDao;
import service.UserDao;
import session.UserSession;
import view.MainNavigator;

public class EditController implements Initializable {
	
	view.MainNavigator controller;
	
	public User currentUser;
	
	@FXML
	private TextField firstNameTxt;
	@FXML
	private TextField lastNameTxt;
	@FXML
	private DatePicker dobPicker;
	@FXML
	private Label emailTxt;
	@FXML
	private PasswordField passwordTxt;
	@FXML
	private PasswordField confirmPasswordTxt;
	@FXML
	private Label wrongLabel;
	@FXML
	private Button recentGamesButton;
	
	private UserSession session = UserSession.getInstance();
	
	public EditController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
		UserDao usrDao = daoFact.getUserDao();
		User user = usrDao.findUser(session.getUserEmail());
		
		this.firstNameTxt.setText(user.getFirstName());
		this.lastNameTxt.setText(user.getLastName());
		this.emailTxt.setText(user.getEmail());
		this.passwordTxt.setText(user.getPassword());
		this.confirmPasswordTxt.setText(user.getPassword());
		this.dobPicker.setValue(LocalDate.parse(user.getDob()));
		
		SportDao sportDao = daoFact.getSportDao();
		boolean validSport = sportDao.validSport(session.getUserId());
		recentGamesButton.setVisible(validSport);
	}
	
	/**
	 * Changes the current FXML page to home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome() {
		DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
		UserDao usrDao = daoFact.getUserDao();
		User updateUsr = getUpdateInformation();
		ValidateResult userValidation = updateUsr.validate();
		boolean sessionsMatch = session.getUserEmail().equals(emailTxt.getText());
		
		if (!userValidation.isValid())
			wrongLabel.setText(userValidation.getMessage());
		else if (!sessionsMatch)
			wrongLabel.setText("The current session is invalid. Please log in again.");
		else if (!usrDao.updateUser(updateUsr))
			wrongLabel.setText("Invaild Information");
		else
			MainNavigator.loadScreen(HOME_FXML);
	}
	
	private User getUpdateInformation() {
		return new User(emailTxt.getText(), passwordTxt.getText(),
				confirmPasswordTxt.getText(), firstNameTxt.getText(),
				lastNameTxt.getText(), dobPicker.getValue().toString());
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
	
	/**
	 * Changes the current FXML page to games.fxml
	 *
	 * @param e
	 */
	@FXML
	private void changeToGames() {
		MainNavigator.loadScreen(GAME_FXML);
	}
}
