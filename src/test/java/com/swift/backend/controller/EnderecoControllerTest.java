package com.swift.backend.controller;

import com.swift.backend.model.Endereco;
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.service.EnderecoService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("EnderecoController - Testes Unitários")
class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    private Endereco enderecoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        enderecoMock = new Endereco();
        enderecoMock.setId(1);
        enderecoMock.setDescricao("Rua das Flores, 123 - Centro");
        enderecoMock.setCep("01234-567");
        enderecoMock.setLatitude(new BigDecimal("-23.550520"));
        enderecoMock.setLongitude(new BigDecimal("-46.633308"));
    }

    @Test
    @DisplayName("GET /api/enderecos - Deve retornar todos os endereços com sucesso")
    void testGetAllEnderecosSuccess() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(enderecoMock);
        
        Endereco endereco2 = new Endereco();
        endereco2.setId(2);
        endereco2.setDescricao("Av. Paulista, 1000");
        endereco2.setCep("01310-100");
        endereco2.setLatitude(new BigDecimal("-23.561414"));
        endereco2.setLongitude(new BigDecimal("-46.656667"));
        enderecos.add(endereco2);
        
        when(enderecoService.getAllEnderecos()).thenReturn(enderecos);

        HttpResponse<?> response = enderecoController.getAllEnderecos();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        List<Endereco> responseEnderecos = (List<Endereco>) response.body();
        assertEquals(2, responseEnderecos.size());
        assertEquals("Rua das Flores, 123 - Centro", responseEnderecos.get(0).getDescricao());
        verify(enderecoService, times(1)).getAllEnderecos();
    }

    @Test
    @DisplayName("GET /api/enderecos - Deve retornar lista vazia quando não há endereços")
    void testGetAllEnderecosEmptyList() throws SQLException {
        when(enderecoService.getAllEnderecos()).thenReturn(new ArrayList<>());

        HttpResponse<?> response = enderecoController.getAllEnderecos();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        List<Endereco> responseEnderecos = (List<Endereco>) response.body();
        assertTrue(responseEnderecos.isEmpty());
        verify(enderecoService, times(1)).getAllEnderecos();
    }

    @Test
    @DisplayName("GET /api/enderecos - Deve retornar erro 500 quando ocorrer exceção")
    void testGetAllEnderecosError() throws SQLException {
        when(enderecoService.getAllEnderecos()).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = enderecoController.getAllEnderecos();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(enderecoService, times(1)).getAllEnderecos();
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve retornar endereço por ID com sucesso")
    void testGetEnderecoByIdSuccess() throws SQLException {
        when(enderecoService.getEnderecoById(1)).thenReturn(Optional.of(enderecoMock));

        HttpResponse<?> response = enderecoController.getEnderecoById(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Endereco endereco = (Endereco) response.body();
        assertEquals(1, endereco.getId());
        assertEquals("Rua das Flores, 123 - Centro", endereco.getDescricao());
        verify(enderecoService, times(1)).getEnderecoById(1);
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve retornar 404 quando endereço não encontrado")
    void testGetEnderecoByIdNotFound() throws SQLException {
        when(enderecoService.getEnderecoById(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = enderecoController.getEnderecoById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(enderecoService, times(1)).getEnderecoById(999);
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetEnderecoByIdError() throws SQLException {
        when(enderecoService.getEnderecoById(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = enderecoController.getEnderecoById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(enderecoService, times(1)).getEnderecoById(1);
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve criar endereço com sucesso")
    void testCreateEnderecoSuccess() throws SQLException {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setDescricao("Rua Nova, 456");
        novoEndereco.setCep("12345-678");
        novoEndereco.setLatitude(new BigDecimal("-23.550000"));
        novoEndereco.setLongitude(new BigDecimal("-46.630000"));

        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(3);
        enderecoSalvo.setDescricao(novoEndereco.getDescricao());
        enderecoSalvo.setCep(novoEndereco.getCep());
        enderecoSalvo.setLatitude(novoEndereco.getLatitude());
        enderecoSalvo.setLongitude(novoEndereco.getLongitude());

        when(enderecoService.createEndereco(any(Endereco.class))).thenReturn(enderecoSalvo);

        HttpResponse<?> response = enderecoController.createEndereco(novoEndereco);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Endereco endereco = (Endereco) response.body();
        assertEquals(3, endereco.getId());
        assertEquals("Rua Nova, 456", endereco.getDescricao());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve retornar erro 400 com dados inválidos")
    void testCreateEnderecoInvalidData() throws SQLException {
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setDescricao("");
        enderecoInvalido.setCep("");

        when(enderecoService.createEndereco(any(Endereco.class)))
                .thenThrow(new IllegalArgumentException("Descrição do endereço é obrigatória"));

        HttpResponse<?> response = enderecoController.createEndereco(enderecoInvalido);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateEnderecoError() throws SQLException {
        when(enderecoService.createEndereco(any(Endereco.class)))
                .thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = enderecoController.createEndereco(enderecoMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve atualizar endereço com sucesso")
    void testUpdateEnderecoSuccess() throws SQLException {
        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setDescricao("Rua Atualizada, 789");
        enderecoAtualizado.setCep("98765-432");
        enderecoAtualizado.setLatitude(new BigDecimal("-23.560000"));
        enderecoAtualizado.setLongitude(new BigDecimal("-46.640000"));

        doNothing().when(enderecoService).updateEndereco(eq(1), any(Endereco.class));

        HttpResponse<?> response = enderecoController.updateEndereco(1, enderecoAtualizado);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Endereço atualizado com sucesso", errorResponse.getMessage());
        verify(enderecoService, times(1)).updateEndereco(eq(1), any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve retornar erro 400 quando endereço não encontrado")
    void testUpdateEnderecoNotFound() throws SQLException {
        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setDescricao("Rua Atualizada, 789");
        enderecoAtualizado.setCep("98765-432");

        doThrow(new IllegalArgumentException("Endereço não encontrado com id: 999"))
                .when(enderecoService).updateEndereco(eq(999), any(Endereco.class));

        HttpResponse<?> response = enderecoController.updateEndereco(999, enderecoAtualizado);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(enderecoService, times(1)).updateEndereco(eq(999), any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve retornar erro 400 com dados inválidos")
    void testUpdateEnderecoInvalidData() throws SQLException {
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setDescricao("");
        enderecoInvalido.setCep("");

        doThrow(new IllegalArgumentException("Descrição do endereço é obrigatória"))
                .when(enderecoService).updateEndereco(eq(1), any(Endereco.class));

        HttpResponse<?> response = enderecoController.updateEndereco(1, enderecoInvalido);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(enderecoService, times(1)).updateEndereco(eq(1), any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testUpdateEnderecoError() throws SQLException {
        doThrow(new SQLException("Database error"))
                .when(enderecoService).updateEndereco(anyInt(), any(Endereco.class));

        HttpResponse<?> response = enderecoController.updateEndereco(1, enderecoMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(enderecoService, times(1)).updateEndereco(eq(1), any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve validar campos obrigatórios")
    void testCreateEnderecoMissingCep() throws SQLException {
        Endereco enderecoSemCep = new Endereco();
        enderecoSemCep.setDescricao("Rua sem CEP");

        when(enderecoService.createEndereco(any(Endereco.class)))
                .thenThrow(new IllegalArgumentException("CEP é obrigatório"));

        HttpResponse<?> response = enderecoController.createEndereco(enderecoSemCep);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("CEP é obrigatório", errorResponse.getMessage());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve aceitar endereço sem coordenadas")
    void testCreateEnderecoWithoutCoordinates() throws SQLException {
        Endereco enderecoSemCoordenadas = new Endereco();
        enderecoSemCoordenadas.setDescricao("Rua sem coordenadas");
        enderecoSemCoordenadas.setCep("00000-000");

        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(4);
        enderecoSalvo.setDescricao(enderecoSemCoordenadas.getDescricao());
        enderecoSalvo.setCep(enderecoSemCoordenadas.getCep());

        when(enderecoService.createEndereco(any(Endereco.class))).thenReturn(enderecoSalvo);

        HttpResponse<?> response = enderecoController.createEndereco(enderecoSemCoordenadas);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }
}
