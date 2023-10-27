package com.cuenta_movimientos_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cuenta_movimientos_service.entity.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
  @Query("FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta")
  public Cuenta findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

  @Query("FROM Cuenta c WHERE c.clienteId = :clienteId")
  public List<Cuenta> findByClienteId(@Param("clienteId") String clienteId);
}
