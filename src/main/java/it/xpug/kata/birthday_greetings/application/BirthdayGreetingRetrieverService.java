package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;

import java.time.LocalDate;
import java.util.List;

public interface BirthdayGreetingRetrieverService {
    List<Employee> getListEmployeesWithBirthdayAt(LocalDate date);
}
