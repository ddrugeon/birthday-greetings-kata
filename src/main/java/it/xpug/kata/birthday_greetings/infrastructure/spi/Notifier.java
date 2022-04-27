package it.xpug.kata.birthday_greetings.infrastructure.spi;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.MessagingException;
import java.util.List;

public interface Notifier {
    void sendMessage(String sender,Employee employee) throws NotificationException;
    void sendMessage(String sender,List<Employee> employees) throws NotificationException;
}
