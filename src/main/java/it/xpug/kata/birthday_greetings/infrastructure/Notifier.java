package it.xpug.kata.birthday_greetings.infrastructure;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface Notifier {
    void sendMessage(String sender, String subject, String body, String recipient) throws AddressException, MessagingException;
}
