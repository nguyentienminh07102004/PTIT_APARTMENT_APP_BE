package com.apartmentbuilding.PTIT.Common.ExceptionAdvice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = DataInvalidException.class)
    public ResponseEntity<String> handleDataInvalidException(DataInvalidException exception) {
        return ResponseEntity.status(exception.getExceptionVariable().getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ExceptionVariable exceptionVariable = ExceptionVariable.valueOf(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseEntity.status(exceptionVariable.getStatus()).body(exceptionVariable.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException exception) {
        ExceptionVariable exceptionVariable = ExceptionVariable.valueOf(exception.getMessage());
        return ResponseEntity.status(exceptionVariable.getStatus()).body(exceptionVariable.getMessage());
    }
}
