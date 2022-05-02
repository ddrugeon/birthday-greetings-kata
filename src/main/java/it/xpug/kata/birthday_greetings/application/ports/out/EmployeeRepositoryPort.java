package it.xpug.kata.birthday_greetings.application.ports.out;

import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;

import java.util.List;

public interface EmployeeRepositoryPort {
    List<Employee> findEmployeesWithBirthdayAt(XDate date);
}
