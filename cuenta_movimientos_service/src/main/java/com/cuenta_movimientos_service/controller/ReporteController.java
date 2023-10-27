package com.cuenta_movimientos_service.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuenta_movimientos_service.dto.MovimientoReportDTO;
import com.cuenta_movimientos_service.services.movimiento.MovimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/reportes")
public class ReporteController {
  @Autowired
  private MovimientoService movimientoService;

  @GetMapping
  public ResponseEntity<List<MovimientoReportDTO>> report(
    @RequestParam String clienteId,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid Date fecha1,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid Date fecha2
  ) {
    List<MovimientoReportDTO> movimientos = movimientoService.getReport(clienteId, fecha1, fecha2);

    return ResponseEntity.ok(movimientos);
  }
}
