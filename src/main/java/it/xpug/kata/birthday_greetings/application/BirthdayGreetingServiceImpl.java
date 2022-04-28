package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.Notifier;

import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetingServiceImpl implements BirthdayGreetingNotifierService, BirthdayGreetingRetrieverService {

	private EmployeeRepository employeeRepository;
	private Notifier notifier;

	public BirthdayGreetingServiceImpl(EmployeeRepository employeeRepository, Notifier notifier) {
		this.employeeRepository = employeeRepository;
		this.notifier = notifier;
	}

	public BirthdayGreetingServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void sendGreetings(String sender, LocalDate date) throws NotificationException {
		if (notifier != null) {
			var employees = employeeRepository.findEmployeesWithBirthdayAt(new XDate(date));

			for (Employee e : employees) {
				notifier.sendMessage(sender, e);
			}
		}
	}

	@Override
	public List<Employee> getListEmployeesWithBirthdayAt(LocalDate date) {
		return employeeRepository.findEmployeesWithBirthdayAt(new XDate(date));
	}
}
