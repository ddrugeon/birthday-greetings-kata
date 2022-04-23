package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XDateTest {
	@Test
	public void getters() throws Exception {
		XDate date = new XDate("1789/01/24");
		assertEquals(1, date.getMonth());
		assertEquals(24, date.getDay());
	}

	@Test
	public void isSameDate() throws Exception {
		XDate date = new XDate("1789/01/24");
		XDate sameDay = new XDate("2001/01/24");
		XDate notSameDay = new XDate("1789/01/25");
		XDate notSameMonth = new XDate("1789/02/25");

		assertAll("date",
			() -> assertTrue(date.isSameDay(sameDay)),
			() -> assertFalse(date.isSameDay(notSameDay)),
			() -> assertFalse(date.isSameDay(notSameMonth)));
	}

	@Test
	public void equality() throws Exception {
		XDate base = new XDate("2000/01/02");
		XDate same = new XDate("2000/01/02");
		XDate different = new XDate("2000/01/04");

		assertAll("equality",
				() -> assertFalse(base.equals(null)),
				() -> assertFalse(base.equals("")),
				() -> assertTrue(base.equals(base)),
				() -> assertTrue(base.equals(same)),
				() -> assertFalse(base.equals(different))
		);
	}

}
