package it.xpug.kata.birthday_greetings.infrastructure;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> filterEmployeesWithBirthdayAt(XDate date);
}
