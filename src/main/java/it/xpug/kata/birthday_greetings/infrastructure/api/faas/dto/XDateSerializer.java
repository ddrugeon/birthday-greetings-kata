package it.xpug.kata.birthday_greetings.infrastructure.api.faas.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.xpug.kata.birthday_greetings.application.domain.XDate;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

class XDateSerializer implements JsonSerializer<XDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public JsonElement serialize(XDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate.date()));
    }
}