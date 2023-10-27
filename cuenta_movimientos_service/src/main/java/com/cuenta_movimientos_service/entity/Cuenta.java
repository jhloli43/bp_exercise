package com.cuenta_movimientos_service.entity;

import java.util.List;

import com.cuenta_movimientos_service.dto.CuentaDTO;
import com.cuenta_movimientos_service.enums.TipoCuenta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuenta")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Cuenta {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String numeroCuenta;
  private String tipoCuenta;
  private Double saldoInicial;
  private Double saldoDisponible;
  private boolean estado;
  private String clienteId;

  @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Movimiento> movimientos;

  public void updateSaldo(Double value) {
    this.saldoDisponible = this.saldoDisponible + value;
  }

  public CuentaDTO toDto() {
    CuentaDTO cuentaDTO = new CuentaDTO();

    cuentaDTO.setId(this.getId());
    cuentaDTO.setClienteId(this.getClienteId());
    cuentaDTO.setNumeroCuenta(this.getNumeroCuenta());
    cuentaDTO.setSaldoInicial(this.getSaldoInicial());
    cuentaDTO.setSaldoDisponible(this.getSaldoDisponible());
    cuentaDTO.setTipoCuenta(TipoCuenta.valueOf(this.getTipoCuenta()).label);

    return cuentaDTO;
  }
}
