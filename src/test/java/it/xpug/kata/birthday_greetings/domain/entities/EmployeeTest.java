package it.xpug.kata.birthday_greetings.domain.entities;

import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
	@Test
	public void testBirthday() throws Exception {
		Employee employee = new Employee("foo", "bar", new XDate("1990/01/31"), "a@b.c");

		assertAll("birthday",
				() -> assertFalse(employee.isBirthday(new XDate("2008/01/30")), "not his birthday"),
				() -> assertTrue(employee.isBirthday(new XDate("2008/01/31")), "his birthday")
		);
	}

	@Test
	public void equality() throws Exception {
		Employee base = new Employee("First", "Last", new XDate("1999/09/01"), "first@last.com");
		Employee same = new Employee("First", "Last", new XDate("1999/09/01"), "first@last.com");
		Employee different = new Employee("First", "Last", new XDate("1999/09/01"), "boom@boom.com");

		assertAll("equality",
			() -> assertFalse(base.equals(null)),
			() -> assertFalse(base.equals("")),
			() -> assertTrue(base.equals(base)),
			() -> assertTrue(base.equals(same)),
			() -> assertFalse(base.equals(different))
		);
	}
}
