package it.xpug.kata.birthday_greetings.infrastructure.exceptions;

public class NotificationException extends Exception {

    public NotificationException(Exception e) {
        super(e);
    }
}
