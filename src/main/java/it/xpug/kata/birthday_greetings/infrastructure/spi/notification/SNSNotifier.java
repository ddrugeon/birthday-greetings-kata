package it.xpug.kata.birthday_greetings.infrastructure.spi.notification;

import it.xpug.kata.birthday_greetings.application.ports.out.NotifierPort;
import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.infrastructure.exceptions.NotificationException;
import it.xpug.kata.birthday_greetings.infrastructure.spi.notification.dto.EmployeeSNSModel;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Collection;
import java.util.List;

public class SNSNotifier implements NotifierPort {

    private final SnsClient snsClient;
    private final String topicARN;

    public SNSNotifier(Region region, String topicARN) {
        this.snsClient = SnsClient.builder().region(region).build();
        this.topicARN = topicARN;
    }

    @Override
    public void sendMessage(String sender, Employee employee) throws NotificationException {
        try {
            EmployeeSNSModel model = EmployeeSNSModel.fromDomain(employee);
            PublishRequest request = PublishRequest.builder()
                    .subject(model.subject())
                    .message(model.message())
                    .topicArn(this.topicARN)
                    .build();

            this.snsClient.publish(request);
        } catch (SnsException e) {
            throw new NotificationException(e);
        }
    }

    @Override
    public void sendMessage(String sender, List<Employee> employees) throws NotificationException {
        Collection<PublishBatchRequestEntry> entries = List.of();
        try {
            for (Employee employee: employees) {
                EmployeeSNSModel model = EmployeeSNSModel.fromDomain(employee);
                var request = PublishBatchRequestEntry
                        .builder()
                        .subject(model.subject())
                        .message(model.message())
                        .build();

                entries.add(request);
            }
            PublishBatchRequest request = PublishBatchRequest.
                    builder()
                    .publishBatchRequestEntries(entries)
                    .build();
            this.snsClient.publishBatch(request);
        } catch (SnsException e) {
            throw new NotificationException(e);
        }
    }
}
