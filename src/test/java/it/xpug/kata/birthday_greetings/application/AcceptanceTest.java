package it.xpug.kata.birthday_greetings.application;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import it.xpug.kata.birthday_greetings.application.ports.BirthdayGreetingService;
import it.xpug.kata.birthday_greetings.application.ports.in.BirthdayGreetingNotifierPort;
import it.xpug.kata.birthday_greetings.application.ports.in.BirthdayGreetingRetrieverPort;
import it.xpug.kata.birthday_greetings.infrastructure.api.cli.BirthdayConsoleAdapter;
import it.xpug.kata.birthday_greetings.infrastructure.spi.persistance.EmployeeCSVFileRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.notification.SMTPNotifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AcceptanceTest {

	private static final int NONSTANDARD_PORT = 9999;
	private BirthdayGreetingNotifierPort birthdayGreetingNotifierService;

	private BirthdayGreetingRetrieverPort birthdayGreetingRetrieverService;

	private SimpleSmtpServer mailServer;

	@BeforeEach
	public void setUp() throws Exception {
		mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT);
		URL resource = BirthdayConsoleAdapter.class.getResource("/employee_data.txt");
		String filename = Paths.get(resource.toURI()).toFile().getAbsolutePath();
		birthdayGreetingNotifierService = new BirthdayGreetingService(new EmployeeCSVFileRepository(filename), new SMTPNotifier("localhost", NONSTANDARD_PORT));
		birthdayGreetingRetrieverService = new BirthdayGreetingService(new EmployeeCSVFileRepository(filename));
	}

	@AfterEach
	public void tearDown() throws Exception {
		mailServer.stop();
		Thread.sleep(200);
	}

	@Test
	public void willSendGreetings_whenItsSomebodysBirthday() throws Exception {

		birthdayGreetingNotifierService.sendGreetings("sender@here.com", LocalDate.parse("2008/10/08", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

		assertEquals(1, mailServer.getReceivedEmails().size(), "message not sent?");

		SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmails().get(0);

		assertEquals("Happy Birthday, dear John!", message.getBody());
		assertEquals("Happy Birthday!", message.getHeaderValue("Subject"));
		List<String> recipients = message.getHeaderValues("To");
		assertEquals(1, recipients.size());
		assertEquals("john.doe@foobar.com", recipients.get(0).toString());
	}

	@Test
	public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
		birthdayGreetingNotifierService.sendGreetings("sender@here.com", LocalDate.parse("2008/01/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")));

		assertEquals(0, mailServer.getReceivedEmails().size(), "what? messages?");
	}

	@Test
	public void returnNonEmptyList_whenItsSomebodysBirthday() {
		var employees = birthdayGreetingRetrieverService.getListEmployeesWithBirthdayAt(LocalDate.parse("2008/10/08", DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		assertNotNull(employees);
		assertEquals(1, employees.size());

	}

	@Test
	public void returnEmptyList_whenNobodysBirthday() {
		var employees = birthdayGreetingRetrieverService.getListEmployeesWithBirthdayAt(LocalDate.parse("2008/01/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		assertNotNull(employees);
		assertEquals(0, employees.size());
	}
}
