package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.churakov.graduation.util.ValidationUtil;
import ru.churakov.graduation.util.exception.ApplicationException;
import ru.churakov.graduation.util.exception.ErrorInfo;
import ru.churakov.graduation.util.exception.ErrorType;
import ru.churakov.graduation.util.exception.IllegalRequestDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static ru.churakov.graduation.util.exception.ErrorType.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Map<String, String> MESSAGE_MAP = Map.of(
            "votes_unique_user_date_idx", "вы уже голосовали сегодня",
            "restaurants_unique_name_idx", "ресторан с таким названием уже существует");

    @Autowired
    private MessageUtil messageUtil;

    //400
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorInfo> wrongRequest(HttpServletRequest req, NoHandlerFoundException e) {
        return getResponse(HttpStatus.BAD_REQUEST, req, WRONG_REQUEST, e);
    }

    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String[] details;
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = MESSAGE_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            details = new String[]{entry.isPresent() ? entry.get().getValue() : lowerCaseMsg};
        } else {
            details = null;
        }

        return getResponse(HttpStatus.CONFLICT, req, DATA_ERROR, details);
    }

    //422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorInfo> bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> messageUtil.getMessage(fe))
                .toArray(String[]::new);

        return getResponse(HttpStatus.UNPROCESSABLE_ENTITY, req, VALIDATION_ERROR, details);
    }

    //422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> illegalRequestDataError(HttpServletRequest req, Exception e) {
        return getResponse(HttpStatus.UNPROCESSABLE_ENTITY, req, VALIDATION_ERROR, e);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationErrorHandler(HttpServletRequest req, ApplicationException appEx) {
        return getResponse(appEx.getHttpStatus(), req, appEx.getType(), appEx.getArgs());
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> defaultErrorHandler(HttpServletRequest req, Exception e) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, req, APP_ERROR, e);
    }

    private ResponseEntity<ErrorInfo> getResponse(HttpStatus status, HttpServletRequest req, ErrorType errorType, Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        return getResponse(status, req, errorType, ValidationUtil.getMessage(rootCause));
    }

    private ResponseEntity<ErrorInfo> getResponse(HttpStatus status, HttpServletRequest req, ErrorType errorType, String... details) {
        StringBuilder url = new StringBuilder(req.getRequestURL());
        if (req.getQueryString() != null) {
            url.append("?").append(req.getQueryString());
        }
        return new ResponseEntity<>(new ErrorInfo(url, errorType,
                messageUtil.getMessage(errorType.getErrorCode()),
                details.length != 0 ? details : null), status);
    }
}
