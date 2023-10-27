package com.cliente_persona_services.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  
  private String nombre;
  private String genero;
  private Integer edad;
  private String identificacion;
  private String direccion;
  private String telefono;
}
