package com.cuenta_movimientos_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotDuplicateEntityException extends ResponseStatusException {

  public CannotDuplicateEntityException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
