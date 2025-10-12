package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Categoria;
import com.swift.backend.service.CategoriaService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController() {
        this.categoriaService = new CategoriaService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/categorias", this::getAllCategorias);
        app.get("/api/categorias/{id}", this::getCategoriaById);
        app.post("/api/categorias", this::createCategoria);
        app.put("/api/categorias/{id}", this::updateCategoria);
        app.delete("/api/categorias/{id}", this::deleteCategoria);
    }

    private void getAllCategorias(Context ctx) {
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();
            ctx.json(categorias);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar categorias: " + e.getMessage());
        }
    }

    private void getCategoriaById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
            
            if (categoria.isPresent()) {
                ctx.json(categoria.get());
            } else {
                ctx.status(404).result("Categoria não encontrada");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    private void createCategoria(Context ctx) {
        try {
            Categoria categoria = ctx.bodyAsClass(Categoria.class);
            Categoria created = categoriaService.createCategoria(categoria);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar categoria: " + e.getMessage());
        }
    }

    private void updateCategoria(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Categoria categoria = ctx.bodyAsClass(Categoria.class);
            categoriaService.updateCategoria(id, categoria);
            ctx.status(200).result("Categoria atualizada com sucesso");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    private void deleteCategoria(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = categoriaService.deleteCategoria(id);
            
            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Categoria não encontrada");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao deletar categoria: " + e.getMessage());
        }
    }
}

