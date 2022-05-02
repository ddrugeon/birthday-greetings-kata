package it.xpug.kata.birthday_greetings.infrastructure.api.cli;

import it.xpug.kata.birthday_greetings.application.ports.in.BirthdayGreetingNotifierPort;
import it.xpug.kata.birthday_greetings.application.ports.BirthdayGreetingService;
import it.xpug.kata.birthday_greetings.infrastructure.spi.persistance.EmployeeCSVFileRepository;
import it.xpug.kata.birthday_greetings.application.ports.out.EmployeeRepositoryPort;
import it.xpug.kata.birthday_greetings.infrastructure.spi.notification.SMTPNotifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.application.ports.out.NotifierPort;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;

public class BirthdayConsoleAdapter {
	private BirthdayGreetingNotifierPort birthdayGreetingService;

	public BirthdayConsoleAdapter(BirthdayGreetingNotifierPort service) {
		this.birthdayGreetingService = service;
	}

	public void sendGreetings(String sender) {
		try {
			System.out.println("Sending an email to employees whose birthday is today");
			birthdayGreetingService.sendGreetings(sender, LocalDate.now());
		} catch (NotificationException e) {
			System.err.println("Cannot send email");
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws AddressException, IOException, ParseException, NotificationException, URISyntaxException {
		URL resource = BirthdayConsoleAdapter.class.getResource("/employee_data.txt");
		String filename = Paths.get(resource.toURI()).toFile().getAbsolutePath();

		EmployeeRepositoryPort repository = new EmployeeCSVFileRepository(filename);
		NotifierPort notifier = new SMTPNotifier("localhost", 25);

		BirthdayGreetingNotifierPort service = new BirthdayGreetingService(new EmployeeCSVFileRepository(filename),
				new SMTPNotifier("localhost", 25));

		new BirthdayConsoleAdapter(service).sendGreetings("sender@here.com");
	}
}
