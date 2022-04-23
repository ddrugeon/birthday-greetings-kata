package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.Employee;
import it.xpug.kata.birthday_greetings.domain.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.EmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.Notifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.text.ParseException;

public class BirthdayService {

	private EmployeeRepository employeeRepository;
	private Notifier notifier;

	public BirthdayService(EmployeeRepository employeeRepository, Notifier notifier) {
		this.employeeRepository = employeeRepository;
		this.notifier = notifier;
	}

	public void sendGreetings(XDate xDate) throws NotificationException {
		var employees = employeeRepository.filterEmployeesWithBirthdayAt(xDate);

		for (Employee e : employees) {
			String recipient = e.getEmail();
			String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", e.getFirstName());
			String subject = "Happy Birthday!";
			notifier.sendMessage("sender@here.com", subject, body, recipient);
		}
	}
}
