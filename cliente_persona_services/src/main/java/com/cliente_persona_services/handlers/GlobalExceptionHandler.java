package com.cliente_persona_services.handlers;

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
import org.springframework.web.client.ResourceAccessException;

import com.cliente_persona_services.exceptions.CannotDeleteEntityException;
import com.cliente_persona_services.exceptions.CannotDuplicateEntityException;
import com.cliente_persona_services.exceptions.EntityNotFoundException;

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
    int statusCode = ex.getStatusCode().value();
    String message = ex.getMessage();

    return ResponseEntity.status(statusCode).body(message);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
    int statusCode = ex.getStatusCode().value();
    String message = ex.getMessage();

    return ResponseEntity.status(statusCode).body(message);
  }

  @ExceptionHandler(CannotDeleteEntityException.class)
  public ResponseEntity<String> handleCannotDeleteEntity(CannotDeleteEntityException ex) {
    int statusCode = ex.getStatusCode().value();
    String message = ex.getMessage();

    return ResponseEntity.status(statusCode).body(message);
  }

  @ExceptionHandler(ResourceAccessException.class)
  public ResponseEntity<String> handleResourceAccess(ResourceAccessException ex) {
    String message = "No se pudo acceder a la API externa. Por favor, asegúrese que esté activa.";

    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(message);
  }
}
