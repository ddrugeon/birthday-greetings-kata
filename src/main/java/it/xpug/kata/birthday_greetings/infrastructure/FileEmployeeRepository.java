package it.xpug.kata.birthday_greetings.infrastructure;

import it.xpug.kata.birthday_greetings.domain.Employee;
import it.xpug.kata.birthday_greetings.domain.XDate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeeRepository implements EmployeeRepository {
    private List<Employee> employees;

    public FileEmployeeRepository(String filename) throws IOException, ParseException {
        this.employees = new ArrayList<>();

        this.readFile(filename);
    }

    private void readFile(String filename) throws IOException, ParseException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        var str = in.readLine(); // skip header
        while ((str = in.readLine()) != null) {
            String[] employeeData = str.split(", ");
            var first_name = employeeData[0];
            var last_name = employeeData[1];
            var date_of_birth = employeeData[2];
            var email = employeeData[3];

            var employee = new Employee(last_name, first_name, date_of_birth, email);
            employees.add(employee);
        }
    }

    @Override
    public List<Employee> filterEmployeesWithBirthdayAt(XDate date) {
        return this.employees.stream().filter(e -> e.isBirthday(date)).toList();
    }
}
