package com.swift.backend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Categoria;
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.service.CategoriaService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/categorias")
public class CategoriaController {

    private static final Logger log = LoggerFactory.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;

    @Inject
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Get
    public HttpResponse<?> getAllCategorias() {
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();
            return HttpResponse.ok(categorias);
        } catch (SQLException e) {
            log.error("Erro ao buscar categorias no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar categorias",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar categorias", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getCategoriaById(@PathVariable Integer id) {
        try {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
            
            if (categoria.isPresent()) {
                return HttpResponse.ok(categoria.get());
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Categoria não encontrada", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar categoria",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post
    public HttpResponse<?> createCategoria(@Body Categoria categoria) {
        try {
            Categoria created = categoriaService.createCategoria(categoria);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao criar categoria: {}", e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao criar categoria no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao criar categoria",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar categoria", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateCategoria(@PathVariable Integer id, @Body Categoria categoria) {
        try {
            categoriaService.updateCategoria(id, categoria);
            return HttpResponse.ok(new ErrorResponse("Categoria atualizada com sucesso", "SUCCESS"));
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao atualizar categoria com id {}: {}", id, e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao atualizar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao atualizar categoria",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteCategoria(@PathVariable Integer id) {
        try {
            boolean deleted = categoriaService.deleteCategoria(id);
            
            if (deleted) {
                return HttpResponse.noContent();
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Categoria não encontrada", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao deletar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao deletar categoria",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar categoria com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

