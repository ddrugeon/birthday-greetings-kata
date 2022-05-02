package it.xpug.kata.birthday_greetings.application.domain;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public record XDate(LocalDate date) {

	public XDate() {
		this(LocalDate.now());
	}
	public XDate(String yyyyMMdd) throws ParseException {
		this(LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
	}

	public int getDay() {
		return date.get(ChronoField.DAY_OF_MONTH);
	}

	public int getMonth() {
		return date.get(ChronoField.MONTH_OF_YEAR);
	}

	public boolean isSameDay(XDate anotherDate) {
		return anotherDate.getDay() == this.getDay() && anotherDate.getMonth() == this.getMonth();
	}
}
