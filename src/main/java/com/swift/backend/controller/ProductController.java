package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Product;
import com.swift.backend.service.ProductService;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class ProductController {

    private final ProductService productService;

    public ProductController() {
        this.productService = new ProductService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/api/products", this::getAllProducts);
        app.get("/api/products/{id}", this::getProductById);
        app.get("/api/products/categoria/{categoriaId}", this::getProductsByCategoria);
        app.get("/api/products/search", this::searchProducts);
        app.post("/api/products", this::createProduct);
        app.put("/api/products/{id}", this::updateProduct);
        app.delete("/api/products/{id}", this::deleteProduct);
    }

    private void getAllProducts(Context ctx) {
        try {
            List<Product> products = productService.getAllProducts();
            ctx.json(products);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private void getProductById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Product> product = productService.getProductById(id);
            
            if (product.isPresent()) {
                ctx.json(product.get());
            } else {
                ctx.status(404).result("Produto não encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar produto: " + e.getMessage());
        }
    }

    private void getProductsByCategoria(Context ctx) {
        try {
            Integer categoriaId = Integer.parseInt(ctx.pathParam("categoriaId"));
            List<Product> products = productService.getProductsByCategoria(categoriaId);
            ctx.json(products);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID de categoria inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private void searchProducts(Context ctx) {
        try {
            String nome = ctx.queryParam("nome");
            if (nome == null || nome.isEmpty()) {
                ctx.status(400).result("Parâmetro 'nome' é obrigatório");
                return;
            }
            
            List<Product> products = productService.searchProducts(nome);
            ctx.json(products);
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private void createProduct(Context ctx) {
        try {
            Product product = ctx.bodyAsClass(Product.class);
            Product created = productService.createProduct(product);
            ctx.status(201).json(created);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar produto: " + e.getMessage());
        }
    }

    private void updateProduct(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Product product = ctx.bodyAsClass(Product.class);
            productService.updateProduct(id, product);
            ctx.status(200).result("Produto atualizado com sucesso");
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private void deleteProduct(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = productService.deleteProduct(id);
            
            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Produto não encontrado");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (Exception e) {
            ctx.status(500).result("Erro ao deletar produto: " + e.getMessage());
        }
    }
}
