package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.Notifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

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
			String recipient = e.email();
			String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", e.firstName());
			String subject = "Happy Birthday!";
			notifier.sendMessage("sender@here.com", subject, body, recipient);
		}
	}
}
