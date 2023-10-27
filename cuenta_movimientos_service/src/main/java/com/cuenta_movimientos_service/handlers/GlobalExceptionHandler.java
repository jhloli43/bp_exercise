package com.cuenta_movimientos_service.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cuenta_movimientos_service.exceptions.CannotDuplicateEntityException;
import com.cuenta_movimientos_service.exceptions.CannotSetNegativeValueException;
import com.cuenta_movimientos_service.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();

    errorResponse.put("errors", errors);

    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
      .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CannotDuplicateEntityException.class)
  public ResponseEntity<String> handleCannotDuplicateEntity(CannotDuplicateEntityException ex) {
    String message = ex.getMessage();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
    String message = ex.getMessage();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

  @ExceptionHandler(CannotSetNegativeValueException.class)
  public ResponseEntity<String> handleCannotSetNegativeValue(CannotSetNegativeValueException ex) {
    String message = ex.getMessage();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message = ex.getPropertyName().contains("fecha") ? ex.getPropertyName() + ": Formato de fecha inválida. Por favor, ingresarla nuevamente" : ex.getMessage();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
    int statusCode = ex.getStatusCode().value();
    String message = ex.getMessage();

    return ResponseEntity.status(statusCode).body(message);
  }
}
