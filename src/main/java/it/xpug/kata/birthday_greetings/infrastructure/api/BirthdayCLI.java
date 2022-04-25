package it.xpug.kata.birthday_greetings.infrastructure.api;

import it.xpug.kata.birthday_greetings.application.BirthdayService;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.FileEmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.MailNotifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;

public class BirthdayCLI {

	public static void main(String[] args) throws AddressException, IOException, ParseException, NotificationException, URISyntaxException {
		URL resource = BirthdayCLI.class.getResource("/employee_data.txt");
		String filename = Paths.get(resource.toURI()).toFile().getAbsolutePath();

		BirthdayService service = new BirthdayService(new FileEmployeeRepository(filename),
				new MailNotifier("localhost", 25));
		service.sendGreetings(new XDate());
	}

}
