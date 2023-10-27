package com.cuenta_movimientos_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MovimientoInputDTO {

  @NotBlank(message = "El número de cuenta no puede estar vacía")
  @Pattern(regexp = "[\\d]+", message = "El número de cuenta debe ser numérico")
  @Size(min = 6, message = "El número de cuenta debe ser un número de mínimo 6 dígitos")
  private String numeroCuenta;

  @NotNull(message = "El valor no puede ser null")
  private Double valor;
}
