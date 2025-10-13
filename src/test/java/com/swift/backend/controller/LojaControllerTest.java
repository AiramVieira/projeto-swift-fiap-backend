package com.swift.backend.controller;

import com.swift.backend.model.Loja;
import com.swift.backend.model.Product;
import com.swift.backend.service.LojaService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("LojaController - Testes Unitários")
class LojaControllerTest {

    @Mock
    private LojaService lojaService;

    @InjectMocks
    private LojaController lojaController;

    private Loja lojaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        lojaMock = new Loja();
        lojaMock.setId(1);
        lojaMock.setEnderecoId(1);
    }

    @Test
    @DisplayName("GET /api/lojas - Deve retornar todas as lojas com sucesso")
    void testGetAllLojas_Success() throws SQLException {
        List<Loja> lojas = new ArrayList<>();
        lojas.add(lojaMock);
        
        when(lojaService.getAllLojas()).thenReturn(lojas);

        HttpResponse<?> response = lojaController.getAllLojas();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        @SuppressWarnings("unchecked")
        List<Loja> responseLojas = (List<Loja>) response.body();
        assertEquals(1, responseLojas.size());
        verify(lojaService, times(1)).getAllLojas();
    }

    @Test
    @DisplayName("GET /api/lojas - Deve retornar lista vazia quando não há lojas")
    void testGetAllLojas_EmptyList() throws SQLException {
        when(lojaService.getAllLojas()).thenReturn(new ArrayList<>());

        HttpResponse<?> response = lojaController.getAllLojas();

        assertEquals(HttpStatus.OK, response.getStatus());
        @SuppressWarnings("unchecked")
        List<Loja> responseLojas = (List<Loja>) response.body();
        assertTrue(responseLojas.isEmpty());
        verify(lojaService, times(1)).getAllLojas();
    }

    @Test
    @DisplayName("GET /api/lojas - Deve retornar erro 500 quando ocorrer exceção")
    void testGetAllLojas_Error() throws SQLException {
        when(lojaService.getAllLojas()).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = lojaController.getAllLojas();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(lojaService, times(1)).getAllLojas();
    }

    @Test
    @DisplayName("GET /api/lojas/{id} - Deve retornar loja por ID com sucesso")
    void testGetLojaById_Success() throws SQLException {
        when(lojaService.getLojaById(1)).thenReturn(Optional.of(lojaMock));

        HttpResponse<?> response = lojaController.getLojaById(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Loja loja = (Loja) response.body();
        assertEquals(1, loja.getId());
        verify(lojaService, times(1)).getLojaById(1);
    }

    @Test
    @DisplayName("GET /api/lojas/{id} - Deve retornar 404 quando loja não encontrada")
    void testGetLojaById_NotFound() throws SQLException {
        when(lojaService.getLojaById(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = lojaController.getLojaById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(lojaService, times(1)).getLojaById(999);
    }

    @Test
    @DisplayName("GET /api/lojas/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetLojaById_Error() throws SQLException {
        when(lojaService.getLojaById(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = lojaController.getLojaById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(lojaService, times(1)).getLojaById(1);
    }

    @Test
    @DisplayName("GET /api/lojas/{id}/produtos - Deve retornar produtos da loja")
    void testGetProdutosByLojaId_Success() throws SQLException {
        List<Product> produtos = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        product.setNome("Produto 1");
        produtos.add(product);
        
        when(lojaService.getProdutosByLojaId(1)).thenReturn(produtos);

        HttpResponse<?> response = lojaController.getProdutosByLojaId(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        @SuppressWarnings("unchecked")
        List<Product> responseProdutos = (List<Product>) response.body();
        assertEquals(1, responseProdutos.size());
        verify(lojaService, times(1)).getProdutosByLojaId(1);
    }

    @Test
    @DisplayName("GET /api/lojas/{id}/produtos - Deve retornar lista vazia quando não há produtos")
    void testGetProdutosByLojaId_EmptyList() throws SQLException {
        when(lojaService.getProdutosByLojaId(1)).thenReturn(new ArrayList<>());

        HttpResponse<?> response = lojaController.getProdutosByLojaId(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        @SuppressWarnings("unchecked")
        List<Product> responseProdutos = (List<Product>) response.body();
        assertTrue(responseProdutos.isEmpty());
        verify(lojaService, times(1)).getProdutosByLojaId(1);
    }

    @Test
    @DisplayName("GET /api/lojas/{id}/produtos - Deve retornar erro 500 quando ocorrer exceção")
    void testGetProdutosByLojaId_Error() throws SQLException {
        when(lojaService.getProdutosByLojaId(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = lojaController.getProdutosByLojaId(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(lojaService, times(1)).getProdutosByLojaId(1);
    }

    @Test
    @DisplayName("POST /api/lojas - Deve criar loja com sucesso")
    void testCreateLoja_Success() throws SQLException {
        Loja novaLoja = new Loja();
        novaLoja.setEnderecoId(2);

        Loja lojaSalva = new Loja();
        lojaSalva.setId(2);
        lojaSalva.setEnderecoId(novaLoja.getEnderecoId());

        when(lojaService.createLoja(any(Loja.class))).thenReturn(lojaSalva);

        HttpResponse<?> response = lojaController.createLoja(novaLoja);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Loja loja = (Loja) response.body();
        assertEquals(2, loja.getId());
        verify(lojaService, times(1)).createLoja(any(Loja.class));
    }

    @Test
    @DisplayName("POST /api/lojas - Deve retornar erro 400 com dados inválidos")
    void testCreateLoja_InvalidData() throws SQLException {
        Loja lojaInvalida = new Loja();
        lojaInvalida.setEnderecoId(null);

        when(lojaService.createLoja(any(Loja.class)))
                .thenThrow(new IllegalArgumentException("Endereço é obrigatório"));

        HttpResponse<?> response = lojaController.createLoja(lojaInvalida);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(lojaService, times(1)).createLoja(any(Loja.class));
    }

    @Test
    @DisplayName("POST /api/lojas - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateLoja_Error() throws SQLException {
        when(lojaService.createLoja(any(Loja.class)))
                .thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = lojaController.createLoja(lojaMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(lojaService, times(1)).createLoja(any(Loja.class));
    }
}

