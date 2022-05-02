package it.xpug.kata.birthday_greetings.infrastructure.spi.persistance;

import it.xpug.kata.birthday_greetings.application.ports.out.EmployeeRepositoryPort;
import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.persistance.dto.EmployeeCSVModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeCSVFileRepository implements EmployeeRepositoryPort {
    private List<Employee> employees;

    public EmployeeCSVFileRepository(String filename) throws IOException, ParseException {
        this.employees = new ArrayList<>();

        this.readFile(filename);
    }

    private void readFile(String filename) throws IOException {
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            this.employees = lines.skip(1).map(line -> {
                return EmployeeCSVModel.toDomain(line);
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<Employee> findEmployeesWithBirthdayAt(XDate date) {
        return this.employees.stream().filter(e -> e.isBirthday(date)).collect(Collectors.toList());
    }
}
