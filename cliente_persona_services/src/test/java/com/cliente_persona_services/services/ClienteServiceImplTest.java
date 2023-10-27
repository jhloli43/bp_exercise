package com.cliente_persona_services.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.cliente_persona_services.dto.ClienteDTO;
import com.cliente_persona_services.entity.Cliente;
import com.cliente_persona_services.exceptions.CannotDuplicateEntityException;
import com.cliente_persona_services.exceptions.EntityNotFoundException;
import com.cliente_persona_services.repository.ClienteRepository;

@SpringBootTest
public class ClienteServiceImplTest {
  
  @TestConfiguration
  static class ClienteServiceImplTestContextConfiguration {
    @Bean
    public ClienteService clienteService() {
      return new ClienteServiceImpl();
    }
  }
  
  @MockBean
  private ClienteRepository clienteRepository;

  @Autowired
  private ClienteService clienteService;

  @Mock
  private RestTemplate restTemplate;

  @Value("${environment.cuentasUrl}")
  private String cuentasURL;

  private ClienteDTO clienteDTO;

  @BeforeEach
  public void setup() {
    Cliente cliente1 = new Cliente();

    cliente1.setClienteId("some-cliente-id");
    cliente1.setContraseña("1234");
    cliente1.setDireccion("Calle A");
    cliente1.setEdad(25);
    cliente1.setEstado(true);
    cliente1.setGenero("M");
    cliente1.setId("cliente1");
    cliente1.setIdentificacion("987654321");
    cliente1.setNombre("John Doe");
    cliente1.setTelefono("+51951639392");

    Cliente cliente2 = new Cliente();

    cliente2.setClienteId("some-cliente-id2");
    cliente2.setContraseña("4321");
    cliente2.setDireccion("Calle B");
    cliente2.setEdad(30);
    cliente2.setEstado(true);
    cliente2.setGenero("F");
    cliente2.setId("cliente2");
    cliente2.setIdentificacion("123456789");
    cliente2.setNombre("Maria Fernanda");
    cliente2.setTelefono("+51999639392");

    List<Cliente> clientes = new ArrayList<Cliente>();

    clientes.add(cliente1);
    clientes.add(cliente2);

    clienteDTO = new ClienteDTO();

    clienteDTO.setClienteId("another-cliente-id");
    clienteDTO.setContraseña("abcd");
    clienteDTO.setDireccion("Calle Z");
    clienteDTO.setEdad(40);
    clienteDTO.setGenero("M");
    clienteDTO.setIdentificacion("887654321");
    clienteDTO.setNombre("Juan Perez");
    clienteDTO.setTelefono("+51987666321");

    Cliente cliente3 = new Cliente();

    cliente3.setClienteId(clienteDTO.getClienteId());
    cliente3.setContraseña(clienteDTO.getContraseña());
    cliente3.setDireccion(clienteDTO.getDireccion());
    cliente3.setEdad(clienteDTO.getEdad());
    cliente3.setEstado(true);
    cliente3.setGenero(clienteDTO.getGenero());
    cliente3.setIdentificacion(clienteDTO.getIdentificacion());
    cliente3.setNombre(clienteDTO.getNombre());
    cliente3.setTelefono(clienteDTO.getTelefono());
    cliente3.setId("cliente3");

    String url = this.cuentasURL + "/cuentas/cliente/{clienteId}";

    Mockito.when(clienteRepository.findAll()).thenReturn(clientes);
    Mockito.when(clienteRepository.findByIdentificacion(cliente1.getIdentificacion())).thenReturn(cliente1);
    Mockito.when(clienteRepository.findByIdentificacion("999888777")).thenReturn(null);
    Mockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente3);
    Mockito.when(clienteRepository.findById("111111111")).thenReturn(Optional.empty());
    Mockito.when(clienteRepository.findById(cliente2.getId())).thenReturn(Optional.of(cliente2));
    Mockito.when(clienteRepository.findById(cliente3.getId())).thenReturn(Optional.of(cliente3));
    Mockito.when(restTemplate.getForObject(url, List.class, cliente2.getId())).thenReturn(new ArrayList<>());
  }

  @Test
  public void whenCallListAll_shouldReturnAllClientes() {
    List<ClienteDTO> clientes = clienteService.list();

    Assertions.assertThat(clientes.size()).isEqualTo(2);
  }

  @Test
  public void whenCallSaveAndTheClientExistsWithIdentificacion_shouldThrowException() {
    clienteDTO.setIdentificacion("987654321");
    CannotDuplicateEntityException exception = assertThrows(CannotDuplicateEntityException.class, () -> {clienteService.save(clienteDTO);});

    String expectedMessage = "Este cliente con la identificacion " + clienteDTO.getIdentificacion() + " ya existe";
    String receivedMessage = exception.getMessage();

    assertTrue(receivedMessage.contains(expectedMessage));
  }

  @Test
  public void whenCallSaveAndTheClientNotExist_shouldSave() {
    clienteDTO.setIdentificacion("999888777");
    clienteService.save(clienteDTO);

    verify(clienteRepository, times(1)).save(any(Cliente.class));
  }

  @Test
  public void whenCallDeleteAndTheClientNotExist_shouldThrowExcepcion() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {clienteService.delete("111111111");});

    String expectedMessage = "El cliente con el ID 111111111 no existe";
    String receivedMessage = exception.getMessage();

    assertTrue(receivedMessage.contains(expectedMessage));
  }

  @Test
  public void whenCallFindByIdAndTheClientNotExist_shouldThrowException() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {clienteService.findById("111111111");});

    String expectedMessage = "El cliente con el ID 111111111 no existe";
    String receivedMessage = exception.getMessage();

    assertTrue(receivedMessage.contains(expectedMessage));
  }

  @Test
  public void whenCallFindByIdAndTheClientExists_shouldReturnTheClient() {
    ClienteDTO receivedClienteDTO = clienteService.findById("cliente3");

    assertTrue(receivedClienteDTO.getNombre().contains(clienteDTO.getNombre()));
  }

  @Test
  public void whenCallUpdateAndAllIsOk_shouldUpdateTheClient() {
    clienteDTO.setEdad(55);
    clienteService.update("cliente3", clienteDTO);

    verify(clienteRepository, times(1)).save(any(Cliente.class));
  }

  @Test
  public void whenCallUpdateAndTheClientNotExist_shouldThrowException() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {clienteService.update("111111111", clienteDTO);});

    String expectedMessage = "El cliente con el ID 111111111 no existe";
    String receivedMessage = exception.getMessage();

    assertTrue(receivedMessage.contains(expectedMessage));
  }

  @Test
  public void whenCallUpdateAndTheClientExistsButDuplicatedIdentificacion_shouldThrowException() {
    clienteDTO.setIdentificacion("987654321");
    CannotDuplicateEntityException exception = assertThrows(CannotDuplicateEntityException.class, () -> {clienteService.update("cliente3", clienteDTO);});

    String expectedMessage = "Este cliente con la identificacion 987654321 ya existe";
    String receivedMessage = exception.getMessage();

    assertTrue(receivedMessage.contains(expectedMessage));
  }
}
