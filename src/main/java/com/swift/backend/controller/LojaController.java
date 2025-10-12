package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Loja;
import com.swift.backend.model.Product;
import com.swift.backend.service.LojaService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class LojaController {

    private final LojaService lojaService;

    public LojaController() {
        this.lojaService = new LojaService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/lojas", this::getAllLojas);
        app.get("/api/lojas/{id}", this::getLojaById);
        app.get("/api/lojas/{id}/produtos", this::getProdutosByLojaId);
        app.post("/api/lojas", this::createLoja);
    }

    private void getAllLojas(Context ctx) {
        try {
            List<Loja> lojas = lojaService.getAllLojas();
            ctx.json(lojas);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar lojas: " + e.getMessage());
        }
    }

    private void getLojaById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Loja> loja = lojaService.getLojaById(id);
            
            if (loja.isPresent()) {
                ctx.json(loja.get());
            } else {
                ctx.status(404).result("Loja não encontrada");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar loja: " + e.getMessage());
        }
    }

    private void getProdutosByLojaId(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            List<Product> produtos = lojaService.getProdutosByLojaId(id);
            ctx.json(produtos);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private void createLoja(Context ctx) {
        try {
            Loja loja = ctx.bodyAsClass(Loja.class);
            Loja created = lojaService.createLoja(loja);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar loja: " + e.getMessage());
        }
    }
}

