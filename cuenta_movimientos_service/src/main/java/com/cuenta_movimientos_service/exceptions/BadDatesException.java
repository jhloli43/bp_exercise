package com.cuenta_movimientos_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadDatesException extends ResponseStatusException {
  public BadDatesException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
