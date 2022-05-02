package it.xpug.kata.birthday_greetings.infrastructure.spi.notification.dto;

import it.xpug.kata.birthday_greetings.application.domain.Employee;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmployeeMailModel {
    public static Message fromDomain(Employee employee, String sender, Session session) throws MessagingException {
        Message msg = new MimeMessage(session);
        String recipient = employee.email();
        String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.firstName());
        String subject = "Happy Birthday!";

        msg.setFrom(new InternetAddress(sender));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setText(body);

        return msg;
    }
}
