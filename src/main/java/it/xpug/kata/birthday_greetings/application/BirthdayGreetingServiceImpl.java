package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.Notifier;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import java.time.LocalDate;

public class BirthdayGreetingServiceImpl implements BirthdayGreetingService {

	private EmployeeRepository employeeRepository;
	private Notifier notifier;

	public BirthdayGreetingServiceImpl(EmployeeRepository employeeRepository, Notifier notifier) {
		this.employeeRepository = employeeRepository;
		this.notifier = notifier;
	}

	@Override
	public void sendGreetings(String sender, LocalDate date) throws NotificationException {
		var employees = employeeRepository.findEmployeesWithBirthdayAt(new XDate(date));

		for (Employee e : employees) {
			notifier.sendMessage(sender, e);
		}
	}
}
