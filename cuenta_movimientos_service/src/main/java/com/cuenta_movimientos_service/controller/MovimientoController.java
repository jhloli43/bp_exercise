package com.cuenta_movimientos_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuenta_movimientos_service.dto.MovimientoInputDTO;
import com.cuenta_movimientos_service.services.movimiento.MovimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController {
  @Autowired
  private MovimientoService movimientoService;

  @PostMapping
  public ResponseEntity<String> save(@RequestBody @Valid MovimientoInputDTO movimientoInputDTO) {
    movimientoService.save(movimientoInputDTO.getNumeroCuenta(), movimientoInputDTO.getValor());

    return ResponseEntity.ok("Movimiento registrado!");
  }
}
