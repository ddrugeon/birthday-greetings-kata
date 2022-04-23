package it.xpug.kata.birthday_greetings.infrastructure;

import javax.mail.MessagingException;

public interface Notifier {
    void sendMessage(String sender, String subject, String body, String recipient) throws MessagingException;
}
