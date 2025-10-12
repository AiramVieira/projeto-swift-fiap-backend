package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Loja;
import com.swift.backend.model.Product;
import com.swift.backend.service.LojaService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/lojas")
public class LojaController {

    private final LojaService lojaService;

    @Inject
    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @Get
    public HttpResponse<List<Loja>> getAllLojas() {
        try {
            List<Loja> lojas = lojaService.getAllLojas();
            return HttpResponse.ok(lojas);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getLojaById(@PathVariable Integer id) {
        try {
            Optional<Loja> loja = lojaService.getLojaById(id);
            
            if (loja.isPresent()) {
                return HttpResponse.ok(loja.get());
            } else {
                return HttpResponse.notFound("Loja não encontrada");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}/produtos")
    public HttpResponse<List<Product>> getProdutosByLojaId(@PathVariable Integer id) {
        try {
            List<Product> produtos = lojaService.getProdutosByLojaId(id);
            return HttpResponse.ok(produtos);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<?> createLoja(@Body Loja loja) {
        try {
            Loja created = lojaService.createLoja(loja);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}

