package com.cliente_persona_services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotDeleteEntityException extends ResponseStatusException {
  public CannotDeleteEntityException(String reason) {
    super(HttpStatus.PRECONDITION_FAILED, reason);
  }
}
