package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.domain.Employee;
import it.xpug.kata.birthday_greetings.domain.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.EmployeeRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.ParseException;

public class BirthdayService {

	private EmployeeRepository employeeRepository;

	public BirthdayService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public void sendGreetings(XDate xDate, String smtpHost, int smtpPort) throws IOException, ParseException, AddressException, MessagingException {
		var employees = employeeRepository.filterEmployeesWithBirthdayAt(xDate);

		for (Employee e : employees) {
			String recipient = e.getEmail();
			String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", e.getFirstName());
			String subject = "Happy Birthday!";
			sendMessage(smtpHost, smtpPort, "sender@here.com", subject, body, recipient);
		}
	}

	private void sendMessage(String smtpHost, int smtpPort, String sender, String subject, String body, String recipient) throws AddressException, MessagingException {
		// Create a mail session
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		Session session = Session.getInstance(props, null);

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(sender));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		msg.setSubject(subject);
		msg.setText(body);

		// Send the message
		Transport.send(msg);
	}
}
