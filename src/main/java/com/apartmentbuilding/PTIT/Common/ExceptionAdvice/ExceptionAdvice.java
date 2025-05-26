package com.apartmentbuilding.PTIT.Common.ExceptionAdvice;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.DTO.Response.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = DataInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleDataInvalidException(DataInvalidException exception) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getExceptionVariable().getMessage())
                .build();
        return ResponseEntity.status(exception.getExceptionVariable().getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ExceptionVariable exceptionVariable = ExceptionVariable.valueOf(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exceptionVariable.getMessage())
                .build();
        return ResponseEntity.status(exceptionVariable.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException exception) {
        ExceptionVariable exceptionVariable = ExceptionVariable.valueOf(exception.getMessage());
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exceptionVariable.getMessage())
                .build();
        return ResponseEntity.status(exceptionVariable.getStatus()).body(exceptionResponse);
    }
}
