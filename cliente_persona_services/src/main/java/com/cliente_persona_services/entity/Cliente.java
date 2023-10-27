package com.cliente_persona_services.entity;

import com.cliente_persona_services.dto.ClienteDTO;
import com.cliente_persona_services.enums.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Cliente extends Persona {
  
  private String clienteId;
  private String contraseña;
  private boolean estado;

  public ClienteDTO toDTO() {
    ClienteDTO clienteDTO = new ClienteDTO();

    clienteDTO.setId(this.getId());
    clienteDTO.setClienteId(this.getClienteId());
    clienteDTO.setContraseña(this.getContraseña());
    clienteDTO.setDireccion(this.getDireccion());
    clienteDTO.setEdad(this.getEdad());
    clienteDTO.setGenero(Genero.valueOf(String.valueOf(this.getGenero())).label);
    clienteDTO.setIdentificacion(this.getIdentificacion());
    clienteDTO.setNombre(this.getNombre());
    clienteDTO.setTelefono(this.getTelefono());

    return clienteDTO;
  }
}
