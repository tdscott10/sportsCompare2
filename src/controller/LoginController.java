/**
 * Controller for the Login FXML page
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 16, 2015
 */
package controller;

import static view.MainNavigator.HOME_FXML;
import static view.MainNavigator.PASSWORD_FXML;
import static view.MainNavigator.QCOMPARE_FXML;
import static view.MainNavigator.REG_FXML;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Email;
import model.EmailProvider;
import model.PasswordGenerator;
import model.User;
import service.DaoFactory;
import service.UserDao;
import view.MainNavigator;

public class LoginController implements Initializable {
	
	view.MainNavigator controller;
	
	@FXML
	private TextField emailTxt;
	@FXML
	private PasswordField passwordTxt;
	@FXML
	private Label incorrectLabel;
	@FXML
	private Hyperlink forgotPasswordLink;
	/** Holder of a switchable vista. */
	@FXML
	private BorderPane mainPane;
	
	DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
	UserDao usrDao = daoFact.getUserDao();
	
	public LoginController() {
		controller = new view.MainNavigator();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Changes the current FXML page to register.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToRegistration(ActionEvent e) {
		MainNavigator.loadScreen(REG_FXML);
	}
	
	private void sendPasswordReminder(String destination) {
		// TODO: Load email text from file
		// TODO: register change with database only if an email was sent, and all other operations concluded
		// successfully
		
		User user = usrDao.findUser(destination);
		String sender = EmailProvider.SMTP_USER;
		final Email email = new Email(destination, sender, null, null);
		
        String newPassword = PasswordGenerator.getInstance().generatePassword(7, 36);
		String subject = "Password Reset";
		String body = "The password for "
				+ user.getEmail()
				+ " has been temporarily reset to \""
				+ newPassword
				+ "\" (no quotes). If this request was not made by you, simply ignore this message. Otherwise,you can change your password by logging in via the SportsCompare client.";
		email.setSubject(subject);
		email.setBody(body);
			
		user.setPassword(newPassword);
        user.setPasswordConfirm(newPassword);
		usrDao.updateUser(user);
        
        EmailProvider.getInstance().sendEmail(email);
	}
	
	@FXML
	private void openForgotPassword(ActionEvent e) {
        if(usrDao.userExists(emailTxt.getText())) {
            sendPasswordReminder(emailTxt.getText());
            User currentUser = usrDao.findUser(emailTxt.getText());
            controller.setSessionUserId(currentUser.getId());
            controller.setSessionUserEmail(currentUser.getEmail());
            MainNavigator.loadScreen(PASSWORD_FXML);
        }
        else
            incorrectLabel.setText("Email Address is not registered.");
	}
	
	/**
	 * Changes the current FXML page to home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome(ActionEvent e) {
		if (usrDao.loginUser(emailTxt.getText(), passwordTxt.getText())) {
			User currentUser = usrDao.findUser(emailTxt.getText());
			controller.setSessionUserId(currentUser.getId());
			controller.setSessionUserEmail(currentUser.getEmail());
			MainNavigator.loadScreen(HOME_FXML);
		}
		else {
			forgotPasswordLink.setText("Forgot Password?");
			incorrectLabel.setText("Invaild Email or Password.");
		}
	}
	
	/**
	 * Changes the current FXML page to register.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToQuickCompare(ActionEvent e) {
		MainNavigator.loadScreen(QCOMPARE_FXML);
	}
	
	/**
	 * Replaces the vista displayed in the vista holder with a new vista.
	 *
	 * @param node
	 *            the vista node to be swapped in.
	 */
	public void setScreen(Node node) {
		mainPane.getChildren().setAll(node);
	}
}
