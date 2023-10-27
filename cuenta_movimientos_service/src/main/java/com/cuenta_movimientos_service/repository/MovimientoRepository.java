package com.cuenta_movimientos_service.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cuenta_movimientos_service.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, String> {
  @Query("FROM Movimiento m WHERE m.cuenta.clienteId = :clienteId AND m.fecha BETWEEN :fecha1 AND :fecha2 ORDER BY m.fecha DESC")
  public List<Movimiento> getReport(
    @Param("clienteId") String clienteId,
    @Param("fecha1") Date fecha1,
    @Param("fecha2") Date fecha2
  );
}
