package com.cliente_persona_services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

  public EntityNotFoundException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
