package com.swift.backend.controller;

import com.swift.backend.model.Categoria;
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.service.CategoriaService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("CategoriaController - Testes Unitários")
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoriaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        categoriaMock = new Categoria();
        categoriaMock.setId(1);
        categoriaMock.setDescricao("Eletrônicos");
    }

    @Test
    @DisplayName("GET /api/categorias - Deve retornar todas as categorias com sucesso")
    void testGetAllCategorias_Success() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaMock);
        
        Categoria categoria2 = new Categoria();
        categoria2.setId(2);
        categoria2.setDescricao("Roupas");
        categorias.add(categoria2);
        
        when(categoriaService.getAllCategorias()).thenReturn(categorias);

        HttpResponse<?> response = categoriaController.getAllCategorias();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        @SuppressWarnings("unchecked")
        List<Categoria> responseCategorias = (List<Categoria>) response.body();
        assertEquals(2, responseCategorias.size());
        assertEquals("Eletrônicos", responseCategorias.get(0).getDescricao());
        verify(categoriaService, times(1)).getAllCategorias();
    }

    @Test
    @DisplayName("GET /api/categorias - Deve retornar lista vazia quando não há categorias")
    void testGetAllCategorias_EmptyList() throws SQLException {
        when(categoriaService.getAllCategorias()).thenReturn(new ArrayList<>());

        HttpResponse<?> response = categoriaController.getAllCategorias();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        @SuppressWarnings("unchecked")
        List<Categoria> responseCategorias = (List<Categoria>) response.body();
        assertTrue(responseCategorias.isEmpty());
        verify(categoriaService, times(1)).getAllCategorias();
    }

    @Test
    @DisplayName("GET /api/categorias - Deve retornar erro 500 quando ocorrer exceção")
    void testGetAllCategorias_Error() throws SQLException {
        when(categoriaService.getAllCategorias()).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = categoriaController.getAllCategorias();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(categoriaService, times(1)).getAllCategorias();
    }

    @Test
    @DisplayName("GET /api/categorias/{id} - Deve retornar categoria por ID com sucesso")
    void testGetCategoriaById_Success() throws SQLException {
        when(categoriaService.getCategoriaById(1)).thenReturn(Optional.of(categoriaMock));

        HttpResponse<?> response = categoriaController.getCategoriaById(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Categoria categoria = (Categoria) response.body();
        assertEquals(1, categoria.getId());
        assertEquals("Eletrônicos", categoria.getDescricao());
        verify(categoriaService, times(1)).getCategoriaById(1);
    }

    @Test
    @DisplayName("GET /api/categorias/{id} - Deve retornar 404 quando categoria não encontrada")
    void testGetCategoriaById_NotFound() throws SQLException {
        when(categoriaService.getCategoriaById(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = categoriaController.getCategoriaById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(categoriaService, times(1)).getCategoriaById(999);
    }

    @Test
    @DisplayName("GET /api/categorias/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetCategoriaById_Error() throws SQLException {
        when(categoriaService.getCategoriaById(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = categoriaController.getCategoriaById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(categoriaService, times(1)).getCategoriaById(1);
    }

    @Test
    @DisplayName("POST /api/categorias - Deve criar categoria com sucesso")
    void testCreateCategoria_Success() throws SQLException {
        Categoria novaCategoria = new Categoria();
        novaCategoria.setDescricao("Livros");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(3);
        categoriaSalva.setDescricao(novaCategoria.getDescricao());

        when(categoriaService.createCategoria(any(Categoria.class))).thenReturn(categoriaSalva);

        HttpResponse<?> response = categoriaController.createCategoria(novaCategoria);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Categoria categoria = (Categoria) response.body();
        assertEquals(3, categoria.getId());
        assertEquals("Livros", categoria.getDescricao());
        verify(categoriaService, times(1)).createCategoria(any(Categoria.class));
    }

    @Test
    @DisplayName("POST /api/categorias - Deve retornar erro 400 com dados inválidos")
    void testCreateCategoria_InvalidData() throws SQLException {
        Categoria categoriaInvalida = new Categoria();
        categoriaInvalida.setDescricao("");

        when(categoriaService.createCategoria(any(Categoria.class)))
                .thenThrow(new IllegalArgumentException("Descrição é obrigatória"));

        HttpResponse<?> response = categoriaController.createCategoria(categoriaInvalida);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(categoriaService, times(1)).createCategoria(any(Categoria.class));
    }

    @Test
    @DisplayName("POST /api/categorias - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateCategoria_Error() throws SQLException {
        when(categoriaService.createCategoria(any(Categoria.class)))
                .thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = categoriaController.createCategoria(categoriaMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(categoriaService, times(1)).createCategoria(any(Categoria.class));
    }

    @Test
    @DisplayName("PUT /api/categorias/{id} - Deve atualizar categoria com sucesso")
    void testUpdateCategoria_Success() throws SQLException {
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setDescricao("Eletrônicos Atualizados");

        doNothing().when(categoriaService).updateCategoria(eq(1), any(Categoria.class));

        HttpResponse<?> response = categoriaController.updateCategoria(1, categoriaAtualizada);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Categoria atualizada com sucesso", errorResponse.getMessage());
        verify(categoriaService, times(1)).updateCategoria(eq(1), any(Categoria.class));
    }

    @Test
    @DisplayName("PUT /api/categorias/{id} - Deve retornar erro 400 quando categoria não encontrada")
    void testUpdateCategoria_NotFound() throws SQLException {
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setDescricao("Teste");

        doThrow(new IllegalArgumentException("Categoria não encontrada com id: 999"))
                .when(categoriaService).updateCategoria(eq(999), any(Categoria.class));

        HttpResponse<?> response = categoriaController.updateCategoria(999, categoriaAtualizada);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(categoriaService, times(1)).updateCategoria(eq(999), any(Categoria.class));
    }

    @Test
    @DisplayName("PUT /api/categorias/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testUpdateCategoria_Error() throws SQLException {
        doThrow(new SQLException("Database error"))
                .when(categoriaService).updateCategoria(anyInt(), any(Categoria.class));

        HttpResponse<?> response = categoriaController.updateCategoria(1, categoriaMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(categoriaService, times(1)).updateCategoria(eq(1), any(Categoria.class));
    }

    @Test
    @DisplayName("DELETE /api/categorias/{id} - Deve deletar categoria com sucesso")
    void testDeleteCategoria_Success() throws SQLException {
        when(categoriaService.deleteCategoria(1)).thenReturn(true);

        HttpResponse<?> response = categoriaController.deleteCategoria(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        verify(categoriaService, times(1)).deleteCategoria(1);
    }

    @Test
    @DisplayName("DELETE /api/categorias/{id} - Deve retornar 404 quando categoria não encontrada")
    void testDeleteCategoria_NotFound() throws SQLException {
        when(categoriaService.deleteCategoria(999)).thenReturn(false);

        HttpResponse<?> response = categoriaController.deleteCategoria(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(categoriaService, times(1)).deleteCategoria(999);
    }

    @Test
    @DisplayName("DELETE /api/categorias/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testDeleteCategoria_Error() throws SQLException {
        when(categoriaService.deleteCategoria(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = categoriaController.deleteCategoria(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(categoriaService, times(1)).deleteCategoria(1);
    }
}

