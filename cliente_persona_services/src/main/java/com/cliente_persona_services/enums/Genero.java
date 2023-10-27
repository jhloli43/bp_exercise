package com.cliente_persona_services.enums;

public enum Genero {
  M("Masculino"),
  F("Femenino");

  public final String label;

  private Genero(String label) {
    this.label = label;
  }
}
