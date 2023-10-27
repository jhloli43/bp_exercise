package com.cuenta_movimientos_service.enums;

public enum TipoMovimiento {
  R("Retiro"),
  D("Deposito");

  public final String label;

  private TipoMovimiento(String label) {
    this.label = label;
  }
}
