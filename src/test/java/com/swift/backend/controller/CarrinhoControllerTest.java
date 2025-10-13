package com.swift.backend.controller;

import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.ItemDoCarrinho;
import com.swift.backend.service.CarrinhoService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("CarrinhoController - Testes Unitários")
class CarrinhoControllerTest {

    @Mock
    private CarrinhoService carrinhoService;

    @InjectMocks
    private CarrinhoController carrinhoController;

    private Carrinho carrinhoMock;
    private ItemDoCarrinho itemMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        carrinhoMock = new Carrinho();
        carrinhoMock.setId(1);
        carrinhoMock.setUsuarioId(1);
        
        itemMock = new ItemDoCarrinho();
        itemMock.setId(1);
        itemMock.setProdutoId(1);
        itemMock.setQuantidade(2);
    }

    @Test
    @DisplayName("GET /api/carrinho/usuario/{usuarioId} - Deve retornar carrinho por usuário")
    void testGetCarrinhoByUsuarioIdSuccess() throws SQLException {
        when(carrinhoService.getCarrinhoByUsuarioId(1)).thenReturn(Optional.of(carrinhoMock));

        HttpResponse<?> response = carrinhoController.getCarrinhoByUsuarioId(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Carrinho carrinho = (Carrinho) response.body();
        assertEquals(1, carrinho.getId());
        verify(carrinhoService, times(1)).getCarrinhoByUsuarioId(1);
    }

    @Test
    @DisplayName("GET /api/carrinho/usuario/{usuarioId} - Deve retornar 404 quando carrinho não encontrado")
    void testGetCarrinhoByUsuarioIdNotFound() throws SQLException {
        when(carrinhoService.getCarrinhoByUsuarioId(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = carrinhoController.getCarrinhoByUsuarioId(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(carrinhoService, times(1)).getCarrinhoByUsuarioId(999);
    }

    @Test
    @DisplayName("GET /api/carrinho/usuario/{usuarioId} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetCarrinhoByUsuarioIdError() throws SQLException {
        when(carrinhoService.getCarrinhoByUsuarioId(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = carrinhoController.getCarrinhoByUsuarioId(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(carrinhoService, times(1)).getCarrinhoByUsuarioId(1);
    }

    @Test
    @DisplayName("POST /api/carrinho/{usuarioId} - Deve criar carrinho com sucesso")
    void testCreateCarrinhoSuccess() throws SQLException {
        when(carrinhoService.createCarrinho(1)).thenReturn(carrinhoMock);

        HttpResponse<?> response = carrinhoController.createCarrinho(1);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Carrinho carrinho = (Carrinho) response.body();
        assertEquals(1, carrinho.getId());
        verify(carrinhoService, times(1)).createCarrinho(1);
    }

    @Test
    @DisplayName("POST /api/carrinho/{usuarioId} - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateCarrinhoError() throws SQLException {
        when(carrinhoService.createCarrinho(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = carrinhoController.createCarrinho(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(carrinhoService, times(1)).createCarrinho(1);
    }

    @Test
    @DisplayName("GET /api/carrinho/{carrinhoId}/itens - Deve retornar itens do carrinho")
    void testGetItensDoCarrinhoSuccess() throws SQLException {
        List<ItemDoCarrinho> itens = new ArrayList<>();
        itens.add(itemMock);
        
        when(carrinhoService.getItensDoCarrinho(1)).thenReturn(itens);

        HttpResponse<?> response = carrinhoController.getItensDoCarrinho(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        List<ItemDoCarrinho> responseItens = (List<ItemDoCarrinho>) response.body();
        assertEquals(1, responseItens.size());
        verify(carrinhoService, times(1)).getItensDoCarrinho(1);
    }

    @Test
    @DisplayName("GET /api/carrinho/{carrinhoId}/itens - Deve retornar lista vazia quando não há itens")
    void testGetItensDoCarrinhoEmptyList() throws SQLException {
        when(carrinhoService.getItensDoCarrinho(1)).thenReturn(new ArrayList<>());

        HttpResponse<?> response = carrinhoController.getItensDoCarrinho(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        List<ItemDoCarrinho> responseItens = (List<ItemDoCarrinho>) response.body();
        assertTrue(responseItens.isEmpty());
        verify(carrinhoService, times(1)).getItensDoCarrinho(1);
    }

    @Test
    @DisplayName("GET /api/carrinho/{carrinhoId}/itens - Deve retornar erro 500 quando ocorrer exceção")
    void testGetItensDoCarrinhoError() throws SQLException {
        when(carrinhoService.getItensDoCarrinho(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = carrinhoController.getItensDoCarrinho(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(carrinhoService, times(1)).getItensDoCarrinho(1);
    }

    @Test
    @DisplayName("POST /api/carrinho/{carrinhoId}/itens/{itemId} - Deve adicionar item ao carrinho")
    void testAdicionarItemSuccess() throws SQLException {
        doNothing().when(carrinhoService).adicionarItemAoCarrinho(1, 1);

        HttpResponse<?> response = carrinhoController.adicionarItem(1, 1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Item adicionado ao carrinho", errorResponse.getMessage());
        verify(carrinhoService, times(1)).adicionarItemAoCarrinho(1, 1);
    }

    @Test
    @DisplayName("POST /api/carrinho/{carrinhoId}/itens/{itemId} - Deve retornar erro 500 quando ocorrer exceção")
    void testAdicionarItemError() throws SQLException {
        doThrow(new SQLException("Database error")).when(carrinhoService).adicionarItemAoCarrinho(anyInt(), anyInt());

        HttpResponse<?> response = carrinhoController.adicionarItem(1, 1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(carrinhoService, times(1)).adicionarItemAoCarrinho(1, 1);
    }

    @Test
    @DisplayName("DELETE /api/carrinho/{carrinhoId}/itens/{itemId} - Deve remover item do carrinho")
    void testRemoverItemSuccess() throws SQLException {
        doNothing().when(carrinhoService).removerItemDoCarrinho(1, 1);

        HttpResponse<?> response = carrinhoController.removerItem(1, 1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Item removido do carrinho", errorResponse.getMessage());
        verify(carrinhoService, times(1)).removerItemDoCarrinho(1, 1);
    }

    @Test
    @DisplayName("DELETE /api/carrinho/{carrinhoId}/itens/{itemId} - Deve retornar erro 500 quando ocorrer exceção")
    void testRemoverItemError() throws SQLException {
        doThrow(new SQLException("Database error")).when(carrinhoService).removerItemDoCarrinho(anyInt(), anyInt());

        HttpResponse<?> response = carrinhoController.removerItem(1, 1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(carrinhoService, times(1)).removerItemDoCarrinho(1, 1);
    }
}

