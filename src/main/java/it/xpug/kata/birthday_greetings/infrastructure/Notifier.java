package it.xpug.kata.birthday_greetings.infrastructure;

import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.MessagingException;

public interface Notifier {
    void sendMessage(String sender, String subject, String body, String recipient) throws NotificationException;
}
