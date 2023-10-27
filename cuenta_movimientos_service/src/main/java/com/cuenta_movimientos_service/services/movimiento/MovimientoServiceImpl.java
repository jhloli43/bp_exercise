package com.cuenta_movimientos_service.services.movimiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cuenta_movimientos_service.dto.ClienteDTO;
import com.cuenta_movimientos_service.dto.MovimientoReportDTO;
import com.cuenta_movimientos_service.entity.Cuenta;
import com.cuenta_movimientos_service.entity.Movimiento;
import com.cuenta_movimientos_service.enums.TipoCuenta;
import com.cuenta_movimientos_service.enums.TipoMovimiento;
import com.cuenta_movimientos_service.exceptions.CannotSetNegativeValueException;
import com.cuenta_movimientos_service.exceptions.EntityNotFoundException;
import com.cuenta_movimientos_service.repository.CuentaRepository;
import com.cuenta_movimientos_service.repository.MovimientoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovimientoServiceImpl implements MovimientoService {

  @Autowired
  private CuentaRepository cuentaRepository;

  @Autowired
  private MovimientoRepository movimientoRepository;

  @Value("${environment.clienteURL}")
  private String clienteURL;

  @Override
  public void save(String numeroCuenta, Double valor) {
    log.info("Numero de cuenta: " + numeroCuenta);
    log.info("Valor a depositar/retirar: " + valor);

    Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);

    if (cuenta == null) {
      String message = "No existe este número de cuenta: " + numeroCuenta;
      throw new EntityNotFoundException(message);
    }

    if (cuenta.getSaldoDisponible() + valor < 0) {
      throw new CannotSetNegativeValueException("Saldo no disponible");
    }

    cuenta.updateSaldo(valor);
    cuentaRepository.save(cuenta);

    TipoMovimiento tipoMovimiento = (valor < 0) ? TipoMovimiento.R : TipoMovimiento.D;
    Movimiento movimiento = new Movimiento();

    movimiento.setCuenta(cuenta);
    movimiento.setFecha(new Date());
    movimiento.setSaldo(cuenta.getSaldoDisponible());
    movimiento.setTipoMovimiento(tipoMovimiento.name());
    movimiento.setValor(valor);

    movimientoRepository.save(movimiento);
  }

  @Override
  public List<MovimientoReportDTO> getReport(String clienteId, Date fecha1, Date fecha2) {
    log.info("Report for " + clienteId + " between dates " + fecha1 + " and " + fecha2);
    
    String url = this.clienteURL + "/clientes/{id}";
    RestTemplate restTemplate = new RestTemplate();
    ClienteDTO clienteDTO = restTemplate.getForObject(url, ClienteDTO.class, clienteId);
    String nombreCliente = clienteDTO.getNombre();
    List<Movimiento> movimientos = movimientoRepository.getReport(clienteId, fecha1, fecha2);
    List<MovimientoReportDTO> report = new ArrayList<MovimientoReportDTO>();

    for (Movimiento movimiento: movimientos) {
      MovimientoReportDTO movimientoReportDTO = new MovimientoReportDTO();

      movimientoReportDTO.setCliente(nombreCliente);
      movimientoReportDTO.setEstado(movimiento.getCuenta().isEstado());
      movimientoReportDTO.setFecha(movimiento.getFecha());
      movimientoReportDTO.setMovimiento(movimiento.getValor());
      movimientoReportDTO.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
      movimientoReportDTO.setSaldoDisponible(movimiento.getSaldo());
      movimientoReportDTO.setSaldoInicial(movimiento.getSaldo() - movimiento.getValor());
      movimientoReportDTO.setTipoCuenta(TipoCuenta.valueOf(movimiento.getCuenta().getTipoCuenta()).label);

      report.add(movimientoReportDTO);
    }

    return report;
  }
  
}
