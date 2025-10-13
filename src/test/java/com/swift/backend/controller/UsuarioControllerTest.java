package com.swift.backend.controller;

import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.Usuario;
import com.swift.backend.service.UsuarioService;
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

@DisplayName("UsuarioController - Testes Unitários")
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNome("João");
        usuarioMock.setSobrenome("Silva");
        usuarioMock.setEnderecoId(1);
        usuarioMock.setTelephone("11999999999");
        usuarioMock.setTipo("PF");
    }

    @Test
    @DisplayName("GET /api/usuarios - Deve retornar todos os usuários com sucesso")
    void testGetAllUsuarios_Success() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioMock);
        
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        HttpResponse<?> response = usuarioController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        @SuppressWarnings("unchecked")
        List<Usuario> responseUsuarios = (List<Usuario>) response.body();
        assertEquals(1, responseUsuarios.size());
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    @DisplayName("GET /api/usuarios - Deve retornar lista vazia quando não há usuários")
    void testGetAllUsuarios_EmptyList() throws SQLException {
        when(usuarioService.getAllUsuarios()).thenReturn(new ArrayList<>());

        HttpResponse<?> response = usuarioController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatus());
        @SuppressWarnings("unchecked")
        List<Usuario> responseUsuarios = (List<Usuario>) response.body();
        assertTrue(responseUsuarios.isEmpty());
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    @DisplayName("GET /api/usuarios - Deve retornar erro 500 quando ocorrer exceção")
    void testGetAllUsuarios_Error() throws SQLException {
        when(usuarioService.getAllUsuarios()).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = usuarioController.getAllUsuarios();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(usuarioService, times(1)).getAllUsuarios();
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar usuário por ID com sucesso")
    void testGetUsuarioById_Success() throws SQLException {
        when(usuarioService.getUsuarioById(1)).thenReturn(Optional.of(usuarioMock));

        HttpResponse<?> response = usuarioController.getUsuarioById(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Usuario usuario = (Usuario) response.body();
        assertEquals(1, usuario.getId());
        assertEquals("João", usuario.getNome());
        verify(usuarioService, times(1)).getUsuarioById(1);
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar 404 quando usuário não encontrado")
    void testGetUsuarioById_NotFound() throws SQLException {
        when(usuarioService.getUsuarioById(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = usuarioController.getUsuarioById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(usuarioService, times(1)).getUsuarioById(999);
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetUsuarioById_Error() throws SQLException {
        when(usuarioService.getUsuarioById(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = usuarioController.getUsuarioById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(usuarioService, times(1)).getUsuarioById(1);
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve criar usuário com sucesso")
    void testCreateUsuario_Success() throws SQLException {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Maria");
        novoUsuario.setSobrenome("Santos");
        novoUsuario.setEnderecoId(2);
        novoUsuario.setTelephone("11988888888");
        novoUsuario.setTipo("PF");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(2);
        usuarioSalvo.setNome(novoUsuario.getNome());
        usuarioSalvo.setSobrenome(novoUsuario.getSobrenome());
        usuarioSalvo.setEnderecoId(novoUsuario.getEnderecoId());
        usuarioSalvo.setTelephone(novoUsuario.getTelephone());
        usuarioSalvo.setTipo(novoUsuario.getTipo());

        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(usuarioSalvo);

        HttpResponse<?> response = usuarioController.createUsuario(novoUsuario);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Usuario usuario = (Usuario) response.body();
        assertEquals(2, usuario.getId());
        assertEquals("Maria", usuario.getNome());
        verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve retornar erro 400 com dados inválidos")
    void testCreateUsuario_InvalidData() throws SQLException {
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNome("");

        when(usuarioService.createUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("Nome é obrigatório"));

        HttpResponse<?> response = usuarioController.createUsuario(usuarioInvalido);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateUsuario_Error() throws SQLException {
        when(usuarioService.createUsuario(any(Usuario.class)))
                .thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = usuarioController.createUsuario(usuarioMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - Deve atualizar usuário com sucesso")
    void testUpdateUsuario_Success() throws SQLException {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("João Atualizado");
        usuarioAtualizado.setSobrenome("Silva");

        doNothing().when(usuarioService).updateUsuario(eq(1), any(Usuario.class));

        HttpResponse<?> response = usuarioController.updateUsuario(1, usuarioAtualizado);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Usuário atualizado com sucesso", errorResponse.getMessage());
        verify(usuarioService, times(1)).updateUsuario(eq(1), any(Usuario.class));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - Deve retornar erro 400 quando usuário não encontrado")
    void testUpdateUsuario_NotFound() throws SQLException {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Teste");

        doThrow(new IllegalArgumentException("Usuário não encontrado com id: 999"))
                .when(usuarioService).updateUsuario(eq(999), any(Usuario.class));

        HttpResponse<?> response = usuarioController.updateUsuario(999, usuarioAtualizado);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(usuarioService, times(1)).updateUsuario(eq(999), any(Usuario.class));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testUpdateUsuario_Error() throws SQLException {
        doThrow(new SQLException("Database error"))
                .when(usuarioService).updateUsuario(anyInt(), any(Usuario.class));

        HttpResponse<?> response = usuarioController.updateUsuario(1, usuarioMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(usuarioService, times(1)).updateUsuario(eq(1), any(Usuario.class));
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - Deve deletar usuário com sucesso")
    void testDeleteUsuario_Success() throws SQLException {
        when(usuarioService.deleteUsuario(1)).thenReturn(true);

        HttpResponse<?> response = usuarioController.deleteUsuario(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        verify(usuarioService, times(1)).deleteUsuario(1);
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - Deve retornar 404 quando usuário não encontrado")
    void testDeleteUsuario_NotFound() throws SQLException {
        when(usuarioService.deleteUsuario(999)).thenReturn(false);

        HttpResponse<?> response = usuarioController.deleteUsuario(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(usuarioService, times(1)).deleteUsuario(999);
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testDeleteUsuario_Error() throws SQLException {
        when(usuarioService.deleteUsuario(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = usuarioController.deleteUsuario(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(usuarioService, times(1)).deleteUsuario(1);
    }
}

