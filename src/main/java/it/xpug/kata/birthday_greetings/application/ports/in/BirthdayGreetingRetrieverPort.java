package it.xpug.kata.birthday_greetings.application.ports.in;

import it.xpug.kata.birthday_greetings.application.domain.Employee;

import java.time.LocalDate;
import java.util.List;

public interface BirthdayGreetingRetrieverPort {
    List<Employee> getListEmployeesWithBirthdayAt(LocalDate date);
}
