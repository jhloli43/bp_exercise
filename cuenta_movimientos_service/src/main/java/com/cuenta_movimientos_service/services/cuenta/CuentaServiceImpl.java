package com.cuenta_movimientos_service.services.cuenta;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cuenta_movimientos_service.dto.ClienteDTO;
import com.cuenta_movimientos_service.dto.CuentaClienteDTO;
import com.cuenta_movimientos_service.dto.CuentaDTO;
import com.cuenta_movimientos_service.entity.Cuenta;
import com.cuenta_movimientos_service.exceptions.CannotDuplicateEntityException;
import com.cuenta_movimientos_service.exceptions.EntityNotFoundException;
import com.cuenta_movimientos_service.repository.CuentaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CuentaServiceImpl implements CuentaService {

  @Autowired
  private CuentaRepository cuentaRepository;

  @Value("${environment.clienteURL}")
  private String clienteURL;

  @Override
  public void save(CuentaDTO cuentaDTO) {
    log.info(cuentaDTO.toString());

    boolean isFoundCuenta = cuentaRepository.findByNumeroCuenta(cuentaDTO.getNumeroCuenta()) != null;

    if (isFoundCuenta) {
      String message = "Este número de cuenta no se puede duplicar: " + cuentaDTO.getNumeroCuenta();
      throw new CannotDuplicateEntityException(message);
    }

    Cuenta cuenta = new Cuenta();
    cuenta.setClienteId(cuentaDTO.getClienteId());
    cuenta.setEstado(true);
    cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
    cuenta.setSaldoDisponible(cuentaDTO.getSaldoInicial());
    cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
    cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());

    cuentaRepository.save(cuenta);
  }

  @Override
  public void delete(String numeroCuenta) {
    log.info("Numero de la cuenta a eliminar: " + numeroCuenta);
    
    Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);

    if (cuenta == null) {
      String message = "La cuenta con número de cuenta " + numeroCuenta + " no existe";
      throw new EntityNotFoundException(message);
    }

    String id = cuenta.getId();

    cuentaRepository.deleteById(id);
  }

  @Override
  public List<CuentaDTO> listAll() {
    String url = this.clienteURL + "/clientes/{id}";
    List<Cuenta> tempList = cuentaRepository.findAll();
    List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

    for (Cuenta cuenta: tempList) {
      CuentaDTO cuentaDTO = cuenta.toDto();

      RestTemplate restTemplate = new RestTemplate();
      ClienteDTO clienteDTO = restTemplate.getForObject(url, ClienteDTO.class, cuentaDTO.getClienteId());

      cuentaDTO.setCliente(clienteDTO.getNombre());
      cuentas.add(cuentaDTO);
    }

    return cuentas;
  }

  @Override
  public List<CuentaClienteDTO> listAllByClienteId(String clienteId) {
    log.info("Cuentas del clienteId: " + clienteId);
    
    List<Cuenta> tempList = cuentaRepository.findByClienteId(clienteId);
    List<CuentaClienteDTO> cuentas = new ArrayList<CuentaClienteDTO>();

    for (Cuenta cuenta: tempList) {
      CuentaClienteDTO cuentaClienteDTO = new CuentaClienteDTO(cuenta.getNumeroCuenta(), cuenta.getTipoCuenta());

      cuentas.add(cuentaClienteDTO);
    }

    return cuentas;
  }
  
}
