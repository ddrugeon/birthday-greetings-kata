package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.application.BirthdayService;
import it.xpug.kata.birthday_greetings.domain.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.FileEmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.MailNotifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.text.ParseException;

public class Main {

	public static void main(String[] args) throws AddressException, IOException, ParseException, NotificationException {
		BirthdayService service = new BirthdayService(new FileEmployeeRepository("employee_data.txt"),
				new MailNotifier("localhost", 25));
		service.sendGreetings(new XDate());
	}

}
