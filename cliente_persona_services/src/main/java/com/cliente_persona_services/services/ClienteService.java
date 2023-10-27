package com.cliente_persona_services.services;

import java.util.List;

import com.cliente_persona_services.dto.ClienteDTO;

public interface ClienteService {
  public List<ClienteDTO> list();
  public ClienteDTO findById(String id);
  public void save(ClienteDTO clienteDTO);
  public void delete(String id);
  public void update(String id, ClienteDTO clienteDTO);
}
