package com.cuenta_movimientos_service.services.movimiento;

import java.util.Date;
import java.util.List;

import com.cuenta_movimientos_service.dto.MovimientoReportDTO;

public interface MovimientoService {
  public void save(String numeroCuenta, Double valor);
  public List<MovimientoReportDTO> getReport(String clienteId, Date date1, Date date2);
}
