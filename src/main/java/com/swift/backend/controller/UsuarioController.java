package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Usuario;
import com.swift.backend.service.UsuarioService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Inject
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Get
    public HttpResponse<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            return HttpResponse.ok(usuarios);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getUsuarioById(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
            
            if (usuario.isPresent()) {
                return HttpResponse.ok(usuario.get());
            } else {
                return HttpResponse.notFound("Usuário não encontrado");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<?> createUsuario(@Body Usuario usuario) {
        try {
            Usuario created = usuarioService.createUsuario(usuario);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateUsuario(@PathVariable Integer id, @Body Usuario usuario) {
        try {
            usuarioService.updateUsuario(id, usuario);
            return HttpResponse.ok("Usuário atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteUsuario(@PathVariable Integer id) {
        try {
            boolean deleted = usuarioService.deleteUsuario(id);
            
            if (deleted) {
                return HttpResponse.noContent();
            } else {
                return HttpResponse.notFound("Usuário não encontrado");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}

