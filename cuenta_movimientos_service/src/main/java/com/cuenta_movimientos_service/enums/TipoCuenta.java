package com.cuenta_movimientos_service.enums;

public enum TipoCuenta {
  C("Corriente"),
  A("Ahorros");

  public final String label;

  private TipoCuenta(String label) {
    this.label = label;
  }
}
