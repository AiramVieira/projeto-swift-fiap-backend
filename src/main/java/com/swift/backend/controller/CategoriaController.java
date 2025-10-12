package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Categoria;
import com.swift.backend.service.CategoriaService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Inject
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Get
    public HttpResponse<List<Categoria>> getAllCategorias() {
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();
            return HttpResponse.ok(categorias);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getCategoriaById(@PathVariable Integer id) {
        try {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
            
            if (categoria.isPresent()) {
                return HttpResponse.ok(categoria.get());
            } else {
                return HttpResponse.notFound("Categoria não encontrada");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<?> createCategoria(@Body Categoria categoria) {
        try {
            Categoria created = categoriaService.createCategoria(categoria);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateCategoria(@PathVariable Integer id, @Body Categoria categoria) {
        try {
            categoriaService.updateCategoria(id, categoria);
            return HttpResponse.ok("Categoria atualizada com sucesso");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteCategoria(@PathVariable Integer id) {
        try {
            boolean deleted = categoriaService.deleteCategoria(id);
            
            if (deleted) {
                return HttpResponse.noContent();
            } else {
                return HttpResponse.notFound("Categoria não encontrada");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}

