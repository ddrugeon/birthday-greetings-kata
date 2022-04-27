package it.xpug.kata.birthday_greetings.infrastructure.api.model;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.api.models.EmployeeJSONModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.stream.Stream;

public class EmployeeJSONModelTest {

    @ParameterizedTest
    @MethodSource("employeeToJson")
    public void testFromDomainToJSON(Employee employee, String expected) {
        var json = EmployeeJSONModel.fromDomain(employee);
        assertEquals(expected, json);
    }

    private static Stream<Arguments> employeeToJson() {
        return Stream.of(
            Arguments.arguments(new Employee("John", "Doe", new XDate(LocalDate.of(1982, 10, 8)), "john.doe@foobar.com"), "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"1982-10-08\",\"email\":\"john.doe@foobar.com\"}"),
            Arguments.arguments(new Employee("Mary", "Ann", new XDate(LocalDate.of(1975, 03, 11)), "mary.ann@foobar.com"), "{\"firstName\":\"Mary\",\"lastName\":\"Ann\",\"birthDate\":\"1975-03-11\",\"email\":\"mary.ann@foobar.com\"}")
        );
    }

}
