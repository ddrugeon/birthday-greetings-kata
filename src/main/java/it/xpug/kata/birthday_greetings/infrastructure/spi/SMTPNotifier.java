package it.xpug.kata.birthday_greetings.infrastructure.spi;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.infrastructure.spi.models.EmployeeMailModel;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.List;

public class SMTPNotifier implements Notifier {

    private final String host;
    private final int port;

    public SMTPNotifier(String smtpHost, int smtpPort) {
        this.host = smtpHost;
        this.port = smtpPort;
    }

    @Override
    public void sendMessage(String sender, Employee employee) throws NotificationException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", "" + this.port);
        Session session = Session.getInstance(props, null);

        // Construct the message
        try {
            Message msg = EmployeeMailModel.fromDomain(employee, sender, session);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new NotificationException(e);
        }
    }

    @Override
    public void sendMessage(String sender, List<Employee> employees) throws NotificationException {
        for (Employee employee : employees) {
            sendMessage(sender, employee);
        }
    }
}
