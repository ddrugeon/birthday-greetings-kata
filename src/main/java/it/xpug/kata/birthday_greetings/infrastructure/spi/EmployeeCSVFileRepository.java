package it.xpug.kata.birthday_greetings.infrastructure.spi;

import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.spi.models.EmployeeCSVModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeCSVFileRepository implements EmployeeRepository {
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
