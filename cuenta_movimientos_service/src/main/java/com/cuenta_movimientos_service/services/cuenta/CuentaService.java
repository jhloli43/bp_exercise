package com.cuenta_movimientos_service.services.cuenta;

import java.util.List;

import com.cuenta_movimientos_service.dto.CuentaClienteDTO;
import com.cuenta_movimientos_service.dto.CuentaDTO;

public interface CuentaService {
  public List<CuentaDTO> listAll();
  public List<CuentaClienteDTO> listAllByClienteId(String clienteId);
  public void save(CuentaDTO cuentaDTO);
  public void delete(String numeroCuenta);
}
