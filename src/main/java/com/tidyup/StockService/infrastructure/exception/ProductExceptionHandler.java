package com.tidyup.StockService.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessage>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorMessage::new).toList());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedTypeException(UnexpectedTypeException exception) {
        return ResponseEntity.badRequest().body(new ErrorMessage("",exception.getMessage()));
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception) {
        return ResponseEntity.badRequest().body(new ErrorMessage("", exception.getMessage()));
    }

    @ExceptionHandler(EntityDoesNotExistException.class)
    public ResponseEntity<ErrorMessage> handleEntityDoesNotExistException(EntityDoesNotExistException exception) {
        return ResponseEntity.badRequest().body(new ErrorMessage("", exception.getMessage()));
    }

    @ExceptionHandler(EntityDoesNotMatchException.class)
    public ResponseEntity<ErrorMessage> handleEntityDoesNotMatchException(EntityDoesNotMatchException exception) {
        return ResponseEntity.badRequest().body(new ErrorMessage("", exception.getMessage()));
    }
}
