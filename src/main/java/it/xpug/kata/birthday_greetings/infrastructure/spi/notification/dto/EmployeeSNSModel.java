package it.xpug.kata.birthday_greetings.infrastructure.spi.notification.dto;

import it.xpug.kata.birthday_greetings.application.domain.Employee;

public record EmployeeSNSModel(String subject, String message) {
    public static EmployeeSNSModel fromDomain(Employee employee) {
        return new EmployeeSNSModel( "Happy Birthday!", "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.firstName()));
    }
}
