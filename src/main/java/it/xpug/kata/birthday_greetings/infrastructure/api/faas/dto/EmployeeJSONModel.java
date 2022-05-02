package it.xpug.kata.birthday_greetings.infrastructure.api.faas.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.xpug.kata.birthday_greetings.application.domain.Employee;
import it.xpug.kata.birthday_greetings.application.domain.XDate;

import java.util.List;

public class EmployeeJSONModel {
    private static final GsonBuilder builder = new GsonBuilder().registerTypeAdapter(XDate.class, new XDateSerializer());;
    public static  String fromDomain(Employee employee) {
        Gson gson = builder.create();
        return gson.toJson(employee);
    }

    public static  String fromDomain(List<Employee> employees) {
        Gson gson = builder.create();
        return gson.toJson(employees);
    }
}
