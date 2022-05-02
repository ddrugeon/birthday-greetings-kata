package it.xpug.kata.birthday_greetings.infrastructure.spi.persistance.dto;

import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;

import java.text.ParseException;
import java.util.regex.Pattern;

public class EmployeeCSVModel {
    private static final Pattern pattern = Pattern.compile(",");

    public static Employee toDomain(String rawCSVLine) {
        String[] arr = pattern.split(rawCSVLine);
        var first_name = arr[0].trim();
        var last_name = arr[1].trim();
        XDate date_of_birth = null;
        try {
            date_of_birth = new XDate(arr[2].trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        var email = arr[3].trim();

        return new Employee(last_name, first_name, date_of_birth, email);

    }
}
