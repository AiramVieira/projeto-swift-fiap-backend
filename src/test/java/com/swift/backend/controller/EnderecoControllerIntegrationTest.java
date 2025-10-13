package com.swift.backend.controller;

import com.swift.backend.model.Endereco;
import com.swift.backend.service.EnderecoService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@MicronautTest
@DisplayName("EnderecoController - Testes de Integração")
class EnderecoControllerIntegrationTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    EnderecoService enderecoService;

    private Endereco enderecoMock;

    @MockBean(EnderecoService.class)
    EnderecoService enderecoService() {
        return mock(EnderecoService.class);
    }

    @BeforeEach
    void setUp() {
        // Criar um endereço mock para os testes
        enderecoMock = new Endereco();
        enderecoMock.setId(1);
        enderecoMock.setDescricao("Rua das Flores, 123 - Centro");
        enderecoMock.setCep("01234-567");
        enderecoMock.setLatitude(new BigDecimal("-23.550520"));
        enderecoMock.setLongitude(new BigDecimal("-46.633308"));
    }

    @Test
    @DisplayName("GET /api/enderecos - Deve retornar todos os endereços com sucesso")
    void testGetAllEnderecos_Success() throws SQLException {
        // Arrange
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

        // Act
        HttpRequest<Object> request = HttpRequest.GET("/api/enderecos");
        HttpResponse<List> response = client.toBlocking().exchange(request, List.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(2, response.body().size());
        verify(enderecoService, times(1)).getAllEnderecos();
    }

    @Test
    @DisplayName("GET /api/enderecos - Deve retornar lista vazia quando não há endereços")
    void testGetAllEnderecos_EmptyList() throws SQLException {
        // Arrange
        when(enderecoService.getAllEnderecos()).thenReturn(new ArrayList<>());

        // Act
        HttpRequest<Object> request = HttpRequest.GET("/api/enderecos");
        HttpResponse<List> response = client.toBlocking().exchange(request, List.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertTrue(response.body().isEmpty());
        verify(enderecoService, times(1)).getAllEnderecos();
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve retornar endereço por ID com sucesso")
    void testGetEnderecoById_Success() throws SQLException {
        // Arrange
        when(enderecoService.getEnderecoById(1)).thenReturn(Optional.of(enderecoMock));

        // Act
        HttpRequest<Object> request = HttpRequest.GET("/api/enderecos/1");
        HttpResponse<Endereco> response = client.toBlocking().exchange(request, Endereco.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(1, response.body().getId());
        assertEquals("Rua das Flores, 123 - Centro", response.body().getDescricao());
        assertEquals("01234-567", response.body().getCep());
        verify(enderecoService, times(1)).getEnderecoById(1);
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve retornar 404 quando endereço não encontrado")
    void testGetEnderecoById_NotFound() throws SQLException {
        // Arrange
        when(enderecoService.getEnderecoById(999)).thenReturn(Optional.empty());

        // Act & Assert
        HttpRequest<Object> request = HttpRequest.GET("/api/enderecos/999");
        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request, Endereco.class)
        );
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(enderecoService, times(1)).getEnderecoById(999);
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve criar endereço com sucesso")
    void testCreateEndereco_Success() throws SQLException {
        // Arrange
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

        // Act
        HttpRequest<Endereco> request = HttpRequest.POST("/api/enderecos", novoEndereco);
        HttpResponse<Endereco> response = client.toBlocking().exchange(request, Endereco.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        assertEquals(3, response.body().getId());
        assertEquals("Rua Nova, 456", response.body().getDescricao());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve retornar erro 400 com dados inválidos")
    void testCreateEndereco_InvalidData() throws SQLException {
        // Arrange
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setDescricao("");
        enderecoInvalido.setCep("");

        when(enderecoService.createEndereco(any(Endereco.class)))
                .thenThrow(new IllegalArgumentException("Descrição do endereço é obrigatória"));

        // Act & Assert
        HttpRequest<Endereco> request = HttpRequest.POST("/api/enderecos", enderecoInvalido);
        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request, Endereco.class)
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve atualizar endereço com sucesso")
    void testUpdateEndereco_Success() throws SQLException {
        // Arrange
        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setDescricao("Rua Atualizada, 789");
        enderecoAtualizado.setCep("98765-432");
        enderecoAtualizado.setLatitude(new BigDecimal("-23.560000"));
        enderecoAtualizado.setLongitude(new BigDecimal("-46.640000"));

        doNothing().when(enderecoService).updateEndereco(eq(1), any(Endereco.class));

        // Act
        HttpRequest<Endereco> request = HttpRequest.PUT("/api/enderecos/1", enderecoAtualizado);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Endereço atualizado com sucesso", response.body());
        verify(enderecoService, times(1)).updateEndereco(eq(1), any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve retornar erro 400 quando endereço não encontrado")
    void testUpdateEndereco_NotFound() throws SQLException {
        // Arrange
        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setDescricao("Rua Atualizada, 789");
        enderecoAtualizado.setCep("98765-432");

        doThrow(new IllegalArgumentException("Endereço não encontrado com id: 999"))
                .when(enderecoService).updateEndereco(eq(999), any(Endereco.class));

        // Act & Assert
        HttpRequest<Endereco> request = HttpRequest.PUT("/api/enderecos/999", enderecoAtualizado);
        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request, String.class)
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(enderecoService, times(1)).updateEndereco(eq(999), any(Endereco.class));
    }

    @Test
    @DisplayName("PUT /api/enderecos/{id} - Deve retornar erro 400 com dados inválidos")
    void testUpdateEndereco_InvalidData() throws SQLException {
        // Arrange
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setDescricao("");
        enderecoInvalido.setCep("");

        doThrow(new IllegalArgumentException("Descrição do endereço é obrigatória"))
                .when(enderecoService).updateEndereco(eq(1), any(Endereco.class));

        // Act & Assert
        HttpRequest<Endereco> request = HttpRequest.PUT("/api/enderecos/1", enderecoInvalido);
        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request, String.class)
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(enderecoService, times(1)).updateEndereco(eq(1), any(Endereco.class));
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve aceitar endereço com coordenadas nulas")
    void testCreateEndereco_NullCoordinates() throws SQLException {
        // Arrange
        Endereco enderecoSemCoordenadas = new Endereco();
        enderecoSemCoordenadas.setDescricao("Rua sem coordenadas");
        enderecoSemCoordenadas.setCep("00000-000");

        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(4);
        enderecoSalvo.setDescricao(enderecoSemCoordenadas.getDescricao());
        enderecoSalvo.setCep(enderecoSemCoordenadas.getCep());

        when(enderecoService.createEndereco(any(Endereco.class))).thenReturn(enderecoSalvo);

        // Act
        HttpRequest<Endereco> request = HttpRequest.POST("/api/enderecos", enderecoSemCoordenadas);
        HttpResponse<Endereco> response = client.toBlocking().exchange(request, Endereco.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        assertEquals("Rua sem coordenadas", response.body().getDescricao());
        assertNull(response.body().getLatitude());
        assertNull(response.body().getLongitude());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }

    @Test
    @DisplayName("GET /api/enderecos/{id} - Deve aceitar diferentes tipos de IDs válidos")
    void testGetEnderecoById_DifferentIds() throws SQLException {
        // Test com ID 100
        Endereco endereco100 = new Endereco();
        endereco100.setId(100);
        endereco100.setDescricao("Endereço 100");
        endereco100.setCep("12345-100");

        when(enderecoService.getEnderecoById(100)).thenReturn(Optional.of(endereco100));

        HttpRequest<Object> request = HttpRequest.GET("/api/enderecos/100");
        HttpResponse<Endereco> response = client.toBlocking().exchange(request, Endereco.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(100, response.body().getId());
        verify(enderecoService, times(1)).getEnderecoById(100);
    }

    @Test
    @DisplayName("POST /api/enderecos - Deve validar formato do CEP")
    void testCreateEndereco_InvalidCep() throws SQLException {
        // Arrange
        Endereco enderecoComCepInvalido = new Endereco();
        enderecoComCepInvalido.setDescricao("Rua com CEP inválido");
        enderecoComCepInvalido.setCep(""); // CEP vazio

        when(enderecoService.createEndereco(any(Endereco.class)))
                .thenThrow(new IllegalArgumentException("CEP é obrigatório"));

        // Act & Assert
        HttpRequest<Endereco> request = HttpRequest.POST("/api/enderecos", enderecoComCepInvalido);
        HttpClientResponseException exception = assertThrows(
            HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request, Endereco.class)
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(enderecoService, times(1)).createEndereco(any(Endereco.class));
    }
}

