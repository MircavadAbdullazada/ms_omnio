package az.atl.ms_auth.handler;


import az.atl.ms_auth.exception.*;
import az.atl.ms_auth.model.consts.ExceptionMessages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler  {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.internalServerError", null, locale);
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, request, message);
    }


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handle(UserNotFoundException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.USER_NOT_FOUND.getMessage(), new Object[]{exception.getMessage()}, locale);
        return getResponseEntity(HttpStatus.BAD_REQUEST, request, message);
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    public ResponseEntity<Object> handle(IncorrectPasswordException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.PASSWORD_INCORRECT.getMessage(), null, locale);
        return getResponseEntity(HttpStatus.BAD_REQUEST, request, message);
    }

    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<Object> handle(PasswordMismatchException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.PASSWORD_MISMATCH.getMessage(), new Object[]{exception.getMessage()}, locale);
        return getResponseEntity(HttpStatus.BAD_REQUEST, request, message);
    }

    @ExceptionHandler(value = UserNameExistsException.class)
    public ResponseEntity<Object> handle(UserNameExistsException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.USERNAME_EXISTS.getMessage(), new Object[]{exception.getMessage()}, locale);
        return getResponseEntity(HttpStatus.BAD_REQUEST, request, message);
    }

    @ExceptionHandler(value = UserNameNotFoundException.class)
    public ResponseEntity<Object> handle(UserNameNotFoundException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.USERNAME_NOT_FOUND.getMessage(), new Object[]{exception.getMessage()}, locale);
        return getResponseEntity(HttpStatus.NOT_FOUND, request, message);
    }


    @ExceptionHandler(value = CustomUnauthorizedException.class)
    public ResponseEntity<Object> handle(CustomUnauthorizedException exception, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ExceptionMessages.UNAUTHORIZED_EXCEPTION.getMessage(), null, locale);
        return getResponseEntity(HttpStatus.NOT_FOUND, request, message);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(HttpStatus status, HttpServletRequest request, String message) {
        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .statusCode(status.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, status);
    }

}
