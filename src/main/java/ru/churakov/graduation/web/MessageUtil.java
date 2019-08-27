package ru.churakov.graduation.web;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.churakov.graduation.util.exception.ApplicationException;

@Component
public class MessageUtil {

    private MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String getMessage(MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

    public String getMessage(ApplicationException appEx) {
        return getMessage(appEx.getMsgCode(), appEx.getArgs());
    }
}
