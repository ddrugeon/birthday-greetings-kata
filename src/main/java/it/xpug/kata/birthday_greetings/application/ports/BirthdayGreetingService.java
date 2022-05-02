package it.xpug.kata.birthday_greetings.application.ports;

import it.xpug.kata.birthday_greetings.application.ports.in.BirthdayGreetingNotifierPort;
import it.xpug.kata.birthday_greetings.application.ports.in.BirthdayGreetingRetrieverPort;
import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.application.ports.out.EmployeeRepositoryPort;
import it.xpug.kata.birthday_greetings.application.ports.out.NotifierPort;

import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetingService implements BirthdayGreetingNotifierPort, BirthdayGreetingRetrieverPort {

	private EmployeeRepositoryPort employeeRepositoryPort;
	private NotifierPort notifier;

	public BirthdayGreetingService(EmployeeRepositoryPort employeeRepositoryPort, NotifierPort notifier) {
		this.employeeRepositoryPort = employeeRepositoryPort;
		this.notifier = notifier;
	}

	public BirthdayGreetingService(EmployeeRepositoryPort employeeRepositoryPort) {
		this.employeeRepositoryPort = employeeRepositoryPort;
	}

	@Override
	public void sendGreetings(String sender, LocalDate date) throws NotificationException {
		if (notifier != null) {
			var employees = employeeRepositoryPort.findEmployeesWithBirthdayAt(new XDate(date));

			for (Employee e : employees) {
				notifier.sendMessage(sender, e);
			}
		}
	}

	@Override
	public List<Employee> getListEmployeesWithBirthdayAt(LocalDate date) {
		return employeeRepositoryPort.findEmployeesWithBirthdayAt(new XDate(date));
	}
}
