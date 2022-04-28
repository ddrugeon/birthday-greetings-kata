package it.xpug.kata.birthday_greetings.infrastructure.api;

import com.amazonaws.services.lambda.runtime.Context;
import it.xpug.kata.birthday_greetings.application.BirthdayGreetingRetrieverService;
import it.xpug.kata.birthday_greetings.application.BirthdayGreetingServiceImpl;
import it.xpug.kata.birthday_greetings.domain.entities.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.api.models.EmployeeJSONModel;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeCSVFileRepository;
import it.xpug.kata.birthday_greetings.infrastructure.spi.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.lambda.powertools.logging.Logging;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

/**
 * Handler for requests to Lambda function.
 */
public class BirthdayLambdaAdapter {
    private final static Logger log = LogManager.getLogger(BirthdayLambdaAdapter.class);
    private BirthdayGreetingRetrieverService birthdayGreetingRetrieverService;

    @Logging(logEvent = true, samplingRate = 0.7)
    public String birthdayRetrieverHandleRequest(String input, Context context) {
        try {
            URL resource = BirthdayLambdaAdapter.class.getResource("/employee_data.txt");
            String filename = Paths.get(resource.toURI()).toFile().getAbsolutePath();

            EmployeeRepository repository = new EmployeeCSVFileRepository(filename);
            birthdayGreetingRetrieverService = new BirthdayGreetingServiceImpl(repository);

            List<Employee> employees = birthdayGreetingRetrieverService.getListEmployeesWithBirthdayAt(LocalDate.now());
            return EmployeeJSONModel.fromDomain(employees);

        } catch (IOException | ParseException | URISyntaxException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
