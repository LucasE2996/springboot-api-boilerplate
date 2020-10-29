package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    // Intercepts any body validation errors that happened in the controllers
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormError> handle(MethodArgumentNotValidException exception) {
        List<FormError> formErrors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FormError formError = new FormError(e.getField(), message);
            formErrors.add(formError);
        });

        return formErrors;
    }
}
