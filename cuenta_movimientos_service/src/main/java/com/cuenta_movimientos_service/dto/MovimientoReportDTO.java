package com.cuenta_movimientos_service.dto;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MovimientoReportDTO {
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date fecha;
  private String cliente;
  private String numeroCuenta;
  private String tipoCuenta;
  private Double saldoInicial;
  private boolean estado;
  private Double movimiento;
  private Double saldoDisponible;
}
