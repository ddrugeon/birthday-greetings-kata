package it.xpug.kata.birthday_greetings.application.ports.in;

import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import java.time.LocalDate;

public interface BirthdayGreetingNotifierPort {
    void sendGreetings(String sender, LocalDate date) throws NotificationException;
}
