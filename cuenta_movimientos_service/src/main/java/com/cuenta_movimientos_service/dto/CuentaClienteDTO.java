package com.cuenta_movimientos_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CuentaClienteDTO {
  private String numeroCuenta;
  private String tipoCuenta;
}
