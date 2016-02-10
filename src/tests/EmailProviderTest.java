package tests;

import static org.junit.Assert.assertTrue;
import model.Email;
import model.EmailProvider;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailProviderTest {

	Email email;
	EmailProvider emailProvider;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		emailProvider = EmailProvider.getInstance();
		// String to, String from, String subject, String body
		email = new Email("psoiya@asu.edu", emailProvider.SMTP_USER,
				"Test Email", "This is a test Email.");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emailProviderTest() {
       
		assertTrue(emailProvider.sendEmail(email));
		
		
		
	}


}
