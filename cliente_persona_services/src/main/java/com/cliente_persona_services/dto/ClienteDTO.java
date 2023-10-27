package com.cliente_persona_services.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ClienteDTO {

  private String id;

  @NotBlank(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotBlank(message = "El género no puede estar vacío")
  @Pattern(regexp = "^(M|F)$", message = "El género debe ser M (masculino) o F (femenino)")
  private String genero;

  @NotNull(message = "La edad no puede ser null")
  @Min(value = 1, message = "La edad no puede ser negativa")
  private Integer edad;

  @NotBlank(message = "La identificación no puede estar vacía")
  private String identificacion;

  @NotBlank(message = "La dirección no puede estar vacía")
  private String direccion;

  @NotBlank(message = "El teléfono no puede estar vacío")
  private String telefono;

  @NotBlank(message = "El clienteId no puede estar vacío")
  private String clienteId;

  @NotBlank(message = "La contraseña no puede estar vacía")
  private String contraseña;


  @Override
  public String toString() {
    return "ClienteDTO {" +
      " nombre='" + getNombre() + "'" +
      ", genero='" + getGenero() + "'" +
      ", edad='" + getEdad() + "'" +
      ", identificacion='" + getIdentificacion() + "'" +
      ", direccion='" + getDireccion() + "'" +
      ", telefono='" + getTelefono() + "'" +
      ", clienteId='" + getClienteId() + "'" +
      ", contraseña='" + getContraseña() + "'" +
      "}";
  }
}
