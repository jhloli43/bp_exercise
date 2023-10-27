package com.cliente_persona_services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cliente_persona_services.dto.ClienteDTO;
import com.cliente_persona_services.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public ResponseEntity<List<ClienteDTO>> list() {
    List<ClienteDTO> clientes = clienteService.list();

    return ResponseEntity.ok(clientes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteDTO> findById(@PathVariable String id) {
    ClienteDTO clienteDTO = clienteService.findById(id);

    return ResponseEntity.ok(clienteDTO);
  }

  @PostMapping
  public ResponseEntity<String> save(@RequestBody @Valid ClienteDTO clienteDTO) {
    clienteService.save(clienteDTO);

    return ResponseEntity.ok("Cliente registrado!");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> update(@RequestBody @Valid ClienteDTO clienteDTO, @PathVariable String id) {
    clienteService.update(id, clienteDTO);

    return ResponseEntity.ok("Cliente actualizado!");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    clienteService.delete(id);

    return ResponseEntity.ok("Cliente eliminado!");
  }
}
