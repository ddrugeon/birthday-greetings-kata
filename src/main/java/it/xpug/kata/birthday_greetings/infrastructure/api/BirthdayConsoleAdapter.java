package it.xpug.kata.birthday_greetings.infrastructure.api;

import it.xpug.kata.birthday_greetings.application.BirthdayGreetingService;
import it.xpug.kata.birthday_greetings.application.BirthdayGreetingServiceImpl;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeCSVFileRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.MailNotifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.infrastructure.spi.Notifier;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;

public class BirthdayConsoleAdapter {
	private BirthdayGreetingService birthdayGreetingService;

	public BirthdayConsoleAdapter(BirthdayGreetingService service) {
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

		EmployeeRepository repository = new EmployeeCSVFileRepository(filename);
		Notifier notifier = new MailNotifier("localhost", 25);

		BirthdayGreetingService service = new BirthdayGreetingServiceImpl(new EmployeeCSVFileRepository(filename),
				new MailNotifier("localhost", 25));

		new BirthdayConsoleAdapter(service).sendGreetings("sender@here.com");
	}
}
