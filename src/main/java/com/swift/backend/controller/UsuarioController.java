package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Usuario;
import com.swift.backend.service.UsuarioService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/usuarios", this::getAllUsuarios);
        app.get("/api/usuarios/{id}", this::getUsuarioById);
        app.post("/api/usuarios", this::createUsuario);
        app.put("/api/usuarios/{id}", this::updateUsuario);
        app.delete("/api/usuarios/{id}", this::deleteUsuario);
    }

    private void getAllUsuarios(Context ctx) {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            ctx.json(usuarios);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    private void getUsuarioById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
            
            if (usuario.isPresent()) {
                ctx.json(usuario.get());
            } else {
                ctx.status(404).result("Usuário não encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void createUsuario(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario created = usuarioService.createUsuario(usuario);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar usuário: " + e.getMessage());
        }
    }

    private void updateUsuario(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            usuarioService.updateUsuario(id, usuario);
            ctx.status(200).result("Usuário atualizado com sucesso");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private void deleteUsuario(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = usuarioService.deleteUsuario(id);
            
            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Usuário não encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}

