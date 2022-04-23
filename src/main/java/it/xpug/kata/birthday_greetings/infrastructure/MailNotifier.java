package it.xpug.kata.birthday_greetings.infrastructure;

import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailNotifier implements Notifier {

    private final String host;
    private final int port;

    public MailNotifier(String smtpHost, int smtpPort) {
        this.host = smtpHost;
        this.port = smtpPort;
    }

    @Override
    public void sendMessage(String sender, String subject, String body, String recipient) throws NotificationException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", "" + this.port);
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(body);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new NotificationException(e);
        }

    }
}
