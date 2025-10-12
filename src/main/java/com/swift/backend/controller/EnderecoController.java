package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Endereco;
import com.swift.backend.service.EnderecoService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController() {
        this.enderecoService = new EnderecoService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/enderecos", this::getAllEnderecos);
        app.get("/api/enderecos/{id}", this::getEnderecoById);
        app.post("/api/enderecos", this::createEndereco);
        app.put("/api/enderecos/{id}", this::updateEndereco);
    }

    private void getAllEnderecos(Context ctx) {
        try {
            List<Endereco> enderecos = enderecoService.getAllEnderecos();
            ctx.json(enderecos);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar endereços: " + e.getMessage());
        }
    }

    private void getEnderecoById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Endereco> endereco = enderecoService.getEnderecoById(id);
            
            if (endereco.isPresent()) {
                ctx.json(endereco.get());
            } else {
                ctx.status(404).result("Endereço não encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    private void createEndereco(Context ctx) {
        try {
            Endereco endereco = ctx.bodyAsClass(Endereco.class);
            Endereco created = enderecoService.createEndereco(endereco);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar endereço: " + e.getMessage());
        }
    }

    private void updateEndereco(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Endereco endereco = ctx.bodyAsClass(Endereco.class);
            enderecoService.updateEndereco(id, endereco);
            ctx.status(200).result("Endereço atualizado com sucesso");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao atualizar endereço: " + e.getMessage());
        }
    }
}

