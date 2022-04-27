package it.xpug.kata.birthday_greetings.application;

import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import java.time.LocalDate;

public interface BirthdayGreetingService {
    void sendGreetings(String sender, LocalDate Date) throws NotificationException;
}
