package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ItemDoCarrinho;
import com.swift.backend.service.CarrinhoService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController() {
        this.carrinhoService = new CarrinhoService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/carrinho/usuario/{usuarioId}", this::getCarrinhoByUsuarioId);
        app.post("/api/carrinho/{usuarioId}", this::createCarrinho);
        app.get("/api/carrinho/{carrinhoId}/itens", this::getItensDoCarrinho);
        app.post("/api/carrinho/{carrinhoId}/itens/{itemId}", this::adicionarItem);
        app.delete("/api/carrinho/{carrinhoId}/itens/{itemId}", this::removerItem);
    }

    private void getCarrinhoByUsuarioId(Context ctx) {
        try {
            Integer usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            Optional<Carrinho> carrinho = carrinhoService.getCarrinhoByUsuarioId(usuarioId);
            
            if (carrinho.isPresent()) {
                ctx.json(carrinho.get());
            } else {
                ctx.status(404).result("Carrinho não encontrado para este usuário");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de usuário inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar carrinho: " + e.getMessage());
        }
    }

    private void createCarrinho(Context ctx) {
        try {
            Integer usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            Carrinho carrinho = carrinhoService.createCarrinho(usuarioId);
            ctx.status(201).json(carrinho);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de usuário inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar carrinho: " + e.getMessage());
        }
    }

    private void getItensDoCarrinho(Context ctx) {
        try {
            Integer carrinhoId = Integer.parseInt(ctx.pathParam("carrinhoId"));
            List<ItemDoCarrinho> itens = carrinhoService.getItensDoCarrinho(carrinhoId);
            ctx.json(itens);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de carrinho inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar itens: " + e.getMessage());
        }
    }

    private void adicionarItem(Context ctx) {
        try {
            Integer carrinhoId = Integer.parseInt(ctx.pathParam("carrinhoId"));
            Integer itemId = Integer.parseInt(ctx.pathParam("itemId"));
            carrinhoService.adicionarItemAoCarrinho(carrinhoId, itemId);
            ctx.status(200).result("Item adicionado ao carrinho");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao adicionar item: " + e.getMessage());
        }
    }

    private void removerItem(Context ctx) {
        try {
            Integer carrinhoId = Integer.parseInt(ctx.pathParam("carrinhoId"));
            Integer itemId = Integer.parseInt(ctx.pathParam("itemId"));
            carrinhoService.removerItemDoCarrinho(carrinhoId, itemId);
            ctx.status(200).result("Item removido do carrinho");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao remover item: " + e.getMessage());
        }
    }
}

