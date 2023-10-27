package com.cliente_persona_services.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cliente_persona_services.dto.ClienteDTO;
import com.cliente_persona_services.entity.Cliente;
import com.cliente_persona_services.exceptions.CannotDeleteEntityException;
import com.cliente_persona_services.exceptions.CannotDuplicateEntityException;
import com.cliente_persona_services.exceptions.EntityNotFoundException;
import com.cliente_persona_services.repository.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Value("${environment.cuentasUrl}")
  private String cuentasURL;

  @Override
  public List<ClienteDTO> list() {
    List<Cliente> tempClientes = clienteRepository.findAll();
    List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();

    for (Cliente cliente: tempClientes) {
      ClienteDTO clienteDTO = cliente.toDTO();

      clientes.add(clienteDTO);
    }

    return clientes;
  }

  @Override
  public void save(ClienteDTO clienteDTO) {
    log.info(clienteDTO.toString());

    boolean isFoundClienteByIdentificacion = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion()) != null;

    if (isFoundClienteByIdentificacion) {
      throw new CannotDuplicateEntityException("Este cliente con la identificacion " + clienteDTO.getIdentificacion() + " ya existe");
    }

    Cliente cliente = new Cliente();
    cliente.setClienteId(clienteDTO.getClienteId());
    cliente.setContraseña(clienteDTO.getContraseña());
    cliente.setDireccion(clienteDTO.getDireccion());
    cliente.setEdad(clienteDTO.getEdad());
    cliente.setEstado(true);
    cliente.setGenero(clienteDTO.getGenero());
    cliente.setIdentificacion(clienteDTO.getIdentificacion());
    cliente.setNombre(clienteDTO.getNombre());
    cliente.setTelefono(clienteDTO.getTelefono());

    clienteRepository.save(cliente);
  }

  @Override
  public void delete(String id) {
    log.info("ID del cliente a eliminar: " + id);
    
    Optional<Cliente> foundCliente = clienteRepository.findById(id);

    if (foundCliente.isEmpty()) {
      throw new EntityNotFoundException("El cliente con el ID " + id + " no existe");
    }

    String url = this.cuentasURL + "/cuentas/cliente/{clienteId}";
    RestTemplate restTemplate = new RestTemplate();
    var cuentas = restTemplate.getForObject(url, List.class, id);

    if (!cuentas.isEmpty()) {
      throw new CannotDeleteEntityException("Este cliente posee cuentas registradas. Primero eliminelas para poder eliminarlo.");
    }

    clienteRepository.deleteById(id);
  }

  @Override
  public void update(String id, ClienteDTO clienteDTO) {
    log.info("ID del cliente a actualizar: " + id);
    
    Optional<Cliente> foundCliente = clienteRepository.findById(id);

    if (foundCliente.isEmpty()) {
      throw new EntityNotFoundException("El cliente con el ID " + id + " no existe");
    }

    Cliente cliente = foundCliente.get();

    if (!clienteDTO.getIdentificacion().equals(cliente.getIdentificacion())) {
      boolean isFoundClienteByIdentificacion = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion()) != null;

      if (isFoundClienteByIdentificacion) {
        throw new CannotDuplicateEntityException("Este cliente con la identificacion " + clienteDTO.getIdentificacion() + " ya existe");
      }
    }

    cliente.setClienteId(clienteDTO.getClienteId());
    cliente.setContraseña(clienteDTO.getContraseña());
    cliente.setDireccion(clienteDTO.getDireccion());
    cliente.setEdad(clienteDTO.getEdad());
    cliente.setEstado(true);
    cliente.setGenero(clienteDTO.getGenero());
    cliente.setIdentificacion(clienteDTO.getIdentificacion());
    cliente.setNombre(clienteDTO.getNombre());
    cliente.setTelefono(clienteDTO.getTelefono());

    clienteRepository.save(cliente);
  }

  @Override
  public ClienteDTO findById(String id) {
    log.info("Looking cliente with ID " + id);
    Optional<Cliente> foundCliente = clienteRepository.findById(id);

    if (foundCliente.isEmpty()) {
      throw new EntityNotFoundException("El cliente con el ID " + id + " no existe");
    }

    Cliente cliente = foundCliente.get();

    return cliente.toDTO();
  }
}
