package com.cuenta_movimientos_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuenta_movimientos_service.dto.CuentaClienteDTO;
import com.cuenta_movimientos_service.dto.CuentaDTO;
import com.cuenta_movimientos_service.services.cuenta.CuentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cuentas")
public class CuentaController {
  @Autowired
  private CuentaService cuentaService;

  @GetMapping
  public ResponseEntity<List<CuentaDTO>> list() {
    List<CuentaDTO> list = cuentaService.listAll();

    return ResponseEntity.ok(list);
  }

  @GetMapping("/cliente/{clienteId}")
  public ResponseEntity<List<CuentaClienteDTO>> listAllByClienteId(@PathVariable String clienteId) {
    List<CuentaClienteDTO> list = cuentaService.listAllByClienteId(clienteId);

    return ResponseEntity.ok(list);
  }

  @PostMapping
  public ResponseEntity<String> save(@RequestBody @Valid CuentaDTO cuentaDTO) {
    cuentaService.save(cuentaDTO);

    return ResponseEntity.ok("Cuenta registrada!");
  }

  @DeleteMapping("/{numeroCuenta}")
  public ResponseEntity<String> delete(@PathVariable String numeroCuenta) {
    cuentaService.delete(numeroCuenta);

    return ResponseEntity.ok("Cuenta eliminada!");
  }
}
