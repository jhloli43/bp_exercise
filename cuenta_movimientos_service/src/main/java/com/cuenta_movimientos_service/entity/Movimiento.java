package com.cuenta_movimientos_service.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimiento")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Movimiento {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private Date fecha;
  private String tipoMovimiento;
  private Double valor;
  private Double saldo;

  @ManyToOne
  @JoinColumn(name = "cuenta_id", nullable = false)
  private Cuenta cuenta;
}
