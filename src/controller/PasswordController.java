/**
 * Controller for the Password FXML page
 * 
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Apr 12, 2015
 */
package controller;

import static view.MainNavigator.HOME_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.User;
import service.DaoFactory;
import service.UserDao;
import session.UserSession;
import view.MainNavigator;

public class PasswordController implements Initializable {
	
	view.MainNavigator controller;
    
    @FXML
    private PasswordField tempPasswordTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField confirmPasswordTxt;
    @FXML
    private Label labelText;
    @FXML
    private Label incorrectTxt;
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    UserDao usrDao = daoFact.getUserDao();
	
	public PasswordController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        String message = "A temporary password has been sent to: " + session.getUserEmail();
        labelText.setText(message);
	}
	
	/**
	 * Changes the current FXML page to the home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome(ActionEvent e) {
        User user = usrDao.findUser(session.getUserEmail());
        if(usrDao.loginUser(session.getUserEmail(), tempPasswordTxt.getText())) {
            if(passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
                user.setPassword(passwordTxt.getText());
                if(!passwordTxt.getText().isEmpty()) {
                    usrDao.updateUser(user);
                    MainNavigator.loadScreen(HOME_FXML);
                } else
                    incorrectTxt.setText("Invailid Password.");
            } else
                incorrectTxt.setText("Passwords do not match.");
        } else
            incorrectTxt.setText("Temporary Password is not correct.");
		
	}
	
}
