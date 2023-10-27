package com.cuenta_movimientos_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CuentaDTO {
  private String id;

  @NotBlank(message = "El número de cuenta no puede estar vacía")
  @Pattern(regexp = "[\\d]+", message = "El número de cuenta debe ser numérico")
  @Size(min = 6, message = "El número de cuenta debe ser un número de mínimo 6 dígitos")
  private String numeroCuenta;

  @NotBlank(message = "El tipo de cuenta no puede estar vacía")
  @Pattern(regexp = "^(A|C)$", message = "El tipo de cuenta debe ser A (Ahorros) o C (corriente)")
  private String tipoCuenta;

  @NotNull(message = "El saldo inicial no puede ser null")
  @Min(value = 0, message = "El saldo inicial no puede ser negativo")
  private Double saldoInicial;

  private Double saldoDisponible;

  @NotBlank(message = "El clienteId no puede estar vacía")
  private String clienteId;

  private String cliente;

  @Override
  public String toString() {
    return "Cuenta DTO{" +
      " numeroCuenta='" + getNumeroCuenta() + "'" +
      ", tipoCuenta='" + getTipoCuenta() + "'" +
      ", saldoInicial='" + getSaldoInicial() + "'" +
      ", clienteId='" + getClienteId() + "'" +
      ", cliente='" + getCliente() + "'" +
      "}";
  }
}
