package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Product;
import com.swift.backend.service.ProductService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/products")
public class ProductController {

    private final ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get
    public HttpResponse<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return HttpResponse.ok(products);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getProductById(@PathVariable Integer id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            
            if (product.isPresent()) {
                return HttpResponse.ok(product.get());
            } else {
                return HttpResponse.notFound("Produto não encontrado");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/categoria/{categoriaId}")
    public HttpResponse<?> getProductsByCategoria(@PathVariable Integer categoriaId) {
        try {
            List<Product> products = productService.getProductsByCategoria(categoriaId);
            return HttpResponse.ok(products);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/search")
    public HttpResponse<?> searchProducts(@QueryValue String nome) {
        try {
            if (nome == null || nome.isEmpty()) {
                return HttpResponse.badRequest("Parâmetro 'nome' é obrigatório");
            }
            
            List<Product> products = productService.searchProducts(nome);
            return HttpResponse.ok(products);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<?> createProduct(@Body Product product) {
        try {
            Product created = productService.createProduct(product);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateProduct(@PathVariable Integer id, @Body Product product) {
        try {
            productService.updateProduct(id, product);
            return HttpResponse.ok("Produto atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteProduct(@PathVariable Integer id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            
            if (deleted) {
                return HttpResponse.noContent();
            } else {
                return HttpResponse.notFound("Produto não encontrado");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}
