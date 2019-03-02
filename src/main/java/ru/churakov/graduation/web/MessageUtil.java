package ru.churakov.graduation.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Map;

@Component
public class MessageUtil {

    private static final Map<String, String> MESSAGE_MAP = Map.of(
            "exception.common.notFound", "Не найдена запись",
            "error.dataNotFound", "Данные не найдены",
            "error.wrongRequest", "Неверный запрос",
            "error.dataError", "Ошибка в данных",
            "error.validationError", "Ошибка проверки данных");

    public String getMessage(String code) {
        return MESSAGE_MAP.getOrDefault(code, code);
    }

    public String getMessage(FieldError resolvable) {
        return String.format("%s: %s",
                resolvable.getField(),
                resolvable.getDefaultMessage());
    }
}
