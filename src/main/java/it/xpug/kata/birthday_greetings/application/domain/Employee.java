package it.xpug.kata.birthday_greetings.application.domain;

import java.util.Objects;

public record Employee(String firstName, String lastName, XDate birthDate, String email) {

	public Employee {
		Objects.requireNonNull(firstName, "First Name cannot be null");
		Objects.requireNonNull(lastName, "Last name cannot be null");
		Objects.requireNonNull(birthDate, "Birth date cannot be null");
		Objects.requireNonNull(email, "E-mail cannot be null");
	}
	public boolean isBirthday(XDate today) {
		return today.isSameDay(birthDate);
	}
}
