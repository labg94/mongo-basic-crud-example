package com.cleveritgroup.mongoexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorController {


    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorMessage handleConflict(
            NoSuchElementException ex, WebRequest request) {
        return ErrorMessage.builder()
                           .statusCode(HttpStatus.NOT_FOUND.value())
                           .description(request.getDescription(false))
                           .message(ex.getMessage())
                           .debugMessage(ex.getMessage())
                           .timestamp(LocalDateTime.now()).build();

    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorMessage handleDefaultErrors(
            Exception ex, WebRequest request) {
        return ErrorMessage.builder()
                           .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                           .description(request.getDescription(false))
                           .message("looks like something went wrong")
                           .debugMessage(ex.getMessage())
                           .timestamp(LocalDateTime.now()).build();

    }

}
