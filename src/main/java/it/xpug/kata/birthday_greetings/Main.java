package it.xpug.kata.birthday_greetings;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;

public class Main {

	public static void main(String[] args) throws AddressException, IOException, ParseException, MessagingException, URISyntaxException {
		URL resource = Main.class.getResource("/employee_data.txt");
		String filename = Paths.get(resource.toURI()).toFile().getAbsolutePath();

		BirthdayService service = new BirthdayService();
		service.sendGreetings(filename, new XDate(), "localhost", 25);
	}

}
