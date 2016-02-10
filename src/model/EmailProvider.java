package model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * Allows for the ability to send email messages over SMTP 
 * @author Taylor Scott
 * @version 
 */
public final class EmailProvider {
	
	private static EmailProvider instance = null;

	public final static String SMTP_USER = "sports.comparison@gmail.com";
	private final static String SMTP_HOST = "smtp.gmail.com";
	private final static String SMTP_PORT = "587";
	private final static String SMTP_PASSWORD = "sportsComp";
	
	private EmailProvider() { }
	
	public static EmailProvider getInstance() {
		if (instance == null) {
			instance = new EmailProvider();
		}
		return instance;
	}
	
	/**
	 * Sends an email via STMP
	 * @param email
	 * @return
	 */
	public boolean sendEmail(Email email) {

		boolean sent = false;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", SMTP_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(SMTP_USER,
								SMTP_PASSWORD);
					}
				});

		//check if there is an attachment and branch accordingly 
		if (email.getAttachmentPath() == null || email.getAttachmentPath().equals("") ) {
			sent = sendBasicEmail(session, email);
		} else { 
			sent = sendMultiPartEmail(session, email);
		}

		return sent;
	}
	
	/**
	 * Sends a basic text only email
	 * @param session
	 * @param email
	 * @return
	 */
	private boolean sendBasicEmail(Session session, Email email){
		
		boolean sent = false;
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email.getTo()));
			message.setSubject(email.getSubject());
			message.setText(email.getBody());

			Transport.send(message);

			sent = true;

		} catch (MessagingException mesExp) {
			System.out.println(getClass().getSimpleName()
					+ " has error message = " + mesExp.getMessage());
		}

		return sent;
	}
	
	/**
	 * Sends an email with an attachment
	 * @param session
	 * @param email
	 * @return
	 */
	private boolean sendMultiPartEmail(Session session, Email email) {

		boolean sent = false;

		try {
		
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email.getFrom())); 
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email.getTo()));
			msg.setSubject(email.getSubject());
			
			Multipart multiPart = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			
			String htmlContent = "<b>Message:</b> " + email.getBody();
			htmlContent += "<br><img src=\"cid:comparison\">";
			
			htmlPart.setContent(htmlContent, "text/html");
			multiPart.addBodyPart(htmlPart);
			
			// Create a new part for the attached file
			MimeBodyPart attachment = new MimeBodyPart();
			attachment.attachFile(new File(email.getAttachmentPath()));
			attachment.setHeader("Content-Type", "image/png");
			attachment.setHeader("Content-ID", "<comparison>");
			
			multiPart.addBodyPart(attachment);
			
			msg.setContent(multiPart);
			
			//must set this so class loader knows of the different MIME types
			MailcapCommandMap mCommandMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
			mCommandMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
			mCommandMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
			mCommandMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
			mCommandMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
			mCommandMap.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
			
			Transport.send(msg);

			sent = true;

		} catch (MessagingException mesExp) {
			System.out.println(getClass().getSimpleName()
					+ " has error message = " + mesExp.getMessage());
			mesExp.printStackTrace();
		} catch (IOException e) {
			System.out.println(getClass().getSimpleName()
					+ " has error message = " + e.getMessage());
		}

		return sent;
	}
	

}
