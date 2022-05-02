package it.xpug.kata.birthday_greetings.application.ports.out;

import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import java.util.List;

public interface NotifierPort {
    void sendMessage(String sender,Employee employee) throws NotificationException;
    void sendMessage(String sender,List<Employee> employees) throws NotificationException;
}
