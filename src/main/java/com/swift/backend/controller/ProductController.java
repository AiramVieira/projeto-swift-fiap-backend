package com.swift.backend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.Product;
import com.swift.backend.service.ProductService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get
    public HttpResponse<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return HttpResponse.ok(products);
        } catch (SQLException e) {
            log.error("Erro ao buscar produtos no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar produtos",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produtos", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getProductById(@PathVariable Integer id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            
            if (product.isPresent()) {
                return HttpResponse.ok(product.get());
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Produto não encontrado", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar produto",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/categoria/{categoriaId}")
    public HttpResponse<?> getProductsByCategoria(@PathVariable Integer categoriaId) {
        try {
            List<Product> products = productService.getProductsByCategoria(categoriaId);
            return HttpResponse.ok(products);
        } catch (SQLException e) {
            log.error("Erro ao buscar produtos por categoria: " + categoriaId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar produtos da categoria",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produtos por categoria: " + categoriaId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/search")
    public HttpResponse<?> searchProducts(@QueryValue String nome) {
        try {
            if (nome == null || nome.isEmpty()) {
                ErrorResponse error = new ErrorResponse("Parâmetro 'nome' é obrigatório", "VALIDATION_ERROR");
                return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            List<Product> products = productService.searchProducts(nome);
            return HttpResponse.ok(products);
        } catch (SQLException e) {
            log.error("Erro ao buscar produtos com nome: " + nome, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar produtos",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produtos com nome: " + nome, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post
    public HttpResponse<?> createProduct(@Body Product product) {
        try {
            Product created = productService.createProduct(product);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao criar produto: {}", e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao criar produto no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao criar produto",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar produto", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateProduct(@PathVariable Integer id, @Body Product product) {
        try {
            productService.updateProduct(id, product);
            return HttpResponse.ok(new ErrorResponse("Produto atualizado com sucesso", "SUCCESS"));
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao atualizar produto com id {}: {}", id, e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao atualizar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao atualizar produto",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteProduct(@PathVariable Integer id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            
            if (deleted) {
                return HttpResponse.noContent();
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Produto não encontrado", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao deletar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao deletar produto",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar produto com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
