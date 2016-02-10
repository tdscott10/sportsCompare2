/**
 * Controller for the Compare FXML page
 * 
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package controller;

import static view.MainNavigator.GAME_FXML;
import static view.MainNavigator.HOME_FXML;
import static view.MainNavigator.LOGIN_FXML;
import static view.MainNavigator.QCOMPARESCREEN_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Email;
import model.EmailProvider;
import java.io.File;
import session.UserSession;
import view.MainNavigator;

public class ShareStatController implements Initializable {
	
	private UserSession session = UserSession.getInstance();
	
	@FXML
	private Label errorLbl;
	@FXML
	private Label successLbl;
	@FXML
	private TextField friendEmailTxt;
	@FXML
	private TextArea messageTxt;
	@FXML
	private Button topButton;
	@FXML
	private Button secondButton;
	
	public ShareStatController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (session.getUserId() == -1) {
			topButton.setText("Login");
			secondButton.setText("Compare");
			topButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					MainNavigator.loadScreen(LOGIN_FXML);
				}
			});
			secondButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					MainNavigator.loadScreen(QCOMPARESCREEN_FXML);
				}
			});
		}
	}
	
	/**
	 * Changes the current FXML page to the home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToHome(ActionEvent e) {
		MainNavigator.loadScreen(HOME_FXML);
	}
	
	/**
	 * Changes the current FXML page to the home.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToRecentGames(ActionEvent e) {
		MainNavigator.loadScreen(GAME_FXML);
	}
	
	@FXML
	private void shareStats(ActionEvent e) {
		
		Email email = new Email(friendEmailTxt.getText(), EmailProvider.SMTP_USER,
				"Sports Compare Comparison", messageTxt.getText(),
				session.getScreenshotPath());
		
		System.out.println("screenshot path inside controller = "
				+ email.getAttachmentPath());
		
		EmailProvider emailProvider = EmailProvider.getInstance();
		boolean res = emailProvider.sendEmail(email);
		
		if (res) {
			successLbl.setText("The email was sent successfully.");
            File image = new File(session.getScreenshotPath());
            image.delete();

		}
		else {
			errorLbl.setText("Unable to send the email. Please try again.");
		}
		
	}
	
}
