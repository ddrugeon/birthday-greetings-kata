package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import java.time.LocalDate;
import java.util.List;

public interface BirthdayGreetingNotifierService {
    void sendGreetings(String sender, LocalDate date) throws NotificationException;
}
