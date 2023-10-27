package com.cliente_persona_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cliente_persona_services.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
  @Query("FROM Cliente c WHERE c.identificacion = :identificacion")
  public Cliente findByIdentificacion(@Param("identificacion") String identificacion);
}
