package it.xpug.kata.birthday_greetings.infrastructure.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.xpug.kata.birthday_greetings.application.BirthdayGreetingService;
import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.domain.vo.XDate;
import it.xpug.kata.birthday_greetings.infrastructure.api.models.EmployeeJSONModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.lambda.powertools.logging.Logging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for requests to Lambda function.
 */
public class BirthdayLambdaAdapter {
    private final static Logger log = LogManager.getLogger(BirthdayLambdaAdapter.class);
    private BirthdayGreetingService birthdayGreetingService;

    @Logging(logEvent = true, samplingRate = 0.7)
    public String handleRequest(String input, Context context) {
        List<Employee> employees = new ArrayList<>();
        BufferedReader in = null;
        String str = "";
        try {
            in = new BufferedReader(new FileReader("employee_data.txt"));
            str = in.readLine(); // skip header
            while ((str = in.readLine()) != null) {
                log.info("Parsing new line");
                String[] employeeData = str.split(", ");
                log.info("Current Employee data {}", employeeData);
                Employee employee = new Employee(employeeData[1], employeeData[0], new XDate(employeeData[2]), employeeData[3]);
                log.info("Current Employee {}", employee);
                if (employee.isBirthday(new XDate())) {
                    log.info("Today is {} {}'s birthday", employee.firstName(), employee.lastName());
                    employees.add(employee);
                }
            }
        } catch (FileNotFoundException e) {
            log.error(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            log.error(e);
            throw new RuntimeException(e);
        }

        return EmployeeJSONModel.fromDomain(employees);
    }
}
