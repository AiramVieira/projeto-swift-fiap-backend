package com.swift.backend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.Loja;
import com.swift.backend.model.Product;
import com.swift.backend.service.LojaService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/lojas")
public class LojaController {

    private static final Logger log = LoggerFactory.getLogger(LojaController.class);
    private final LojaService lojaService;

    @Inject
    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @Get
    public HttpResponse<?> getAllLojas() {
        try {
            List<Loja> lojas = lojaService.getAllLojas();
            return HttpResponse.ok(lojas);
        } catch (SQLException e) {
            log.error("Erro ao buscar lojas no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar lojas",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar lojas", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getLojaById(@PathVariable Integer id) {
        try {
            Optional<Loja> loja = lojaService.getLojaById(id);
            
            if (loja.isPresent()) {
                return HttpResponse.ok(loja.get());
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Loja não encontrada", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar loja com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar loja",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar loja com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{id}/produtos")
    public HttpResponse<?> getProdutosByLojaId(@PathVariable Integer id) {
        try {
            List<Product> produtos = lojaService.getProdutosByLojaId(id);
            return HttpResponse.ok(produtos);
        } catch (SQLException e) {
            log.error("Erro ao buscar produtos da loja com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar produtos da loja",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produtos da loja com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post
    public HttpResponse<?> createLoja(@Body Loja loja) {
        try {
            Loja created = lojaService.createLoja(loja);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao criar loja: {}", e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao criar loja no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao criar loja",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar loja", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

