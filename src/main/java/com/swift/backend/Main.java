package com.swift.backend;

import com.swift.backend.controller.CarrinhoController;
import com.swift.backend.controller.CategoriaController;
import com.swift.backend.controller.EnderecoController;
import com.swift.backend.controller.LojaController;
import com.swift.backend.controller.ProductController;
import com.swift.backend.controller.UsuarioController;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
        }).start(8080);

        System.out.println("\n==============================================");
        System.out.println("Swift Backend API está rodando na porta 8080");
        System.out.println("==============================================");
        System.out.println("\nEndpoints disponíveis:");
        System.out.println("\n--- Produtos ---");
        System.out.println("GET    /api/products");
        System.out.println("GET    /api/products/{id}");
        System.out.println("GET    /api/products/categoria/{categoriaId}");
        System.out.println("GET    /api/products/search?nome=xxx");
        System.out.println("POST   /api/products");
        System.out.println("PUT    /api/products/{id}");
        System.out.println("DELETE /api/products/{id}");
        
        System.out.println("\n--- Categorias ---");
        System.out.println("GET    /api/categorias");
        System.out.println("GET    /api/categorias/{id}");
        System.out.println("POST   /api/categorias");
        System.out.println("PUT    /api/categorias/{id}");
        System.out.println("DELETE /api/categorias/{id}");
        
        System.out.println("\n--- Usuários ---");
        System.out.println("GET    /api/usuarios");
        System.out.println("GET    /api/usuarios/{id}");
        System.out.println("POST   /api/usuarios");
        System.out.println("PUT    /api/usuarios/{id}");
        System.out.println("DELETE /api/usuarios/{id}");
        
        System.out.println("\n--- Carrinho ---");
        System.out.println("GET    /api/carrinho/usuario/{usuarioId}");
        System.out.println("POST   /api/carrinho/{usuarioId}");
        System.out.println("GET    /api/carrinho/{carrinhoId}/itens");
        System.out.println("POST   /api/carrinho/{carrinhoId}/itens/{itemId}");
        System.out.println("DELETE /api/carrinho/{carrinhoId}/itens/{itemId}");
        
        System.out.println("\n--- Lojas ---");
        System.out.println("GET    /api/lojas");
        System.out.println("GET    /api/lojas/{id}");
        System.out.println("GET    /api/lojas/{id}/produtos");
        System.out.println("POST   /api/lojas");
        
        System.out.println("\n--- Endereços ---");
        System.out.println("GET    /api/enderecos");
        System.out.println("GET    /api/enderecos/{id}");
        System.out.println("POST   /api/enderecos");
        System.out.println("PUT    /api/enderecos/{id}");
        System.out.println("\n==============================================\n");

        new ProductController().registerRoutes(app);
        new CategoriaController().registerRoutes(app);
        new UsuarioController().registerRoutes(app);
        new CarrinhoController().registerRoutes(app);
        new LojaController().registerRoutes(app);
        new EnderecoController().registerRoutes(app);
        
        app.get("/", ctx -> ctx.result("Swift Backend API - Sistema de Loja Online"));
        app.get("/health", ctx -> ctx.json(new HealthResponse("UP", "API está funcionando")));
    }
    
    static class HealthResponse {
        private String status;
        private String message;
        
        public HealthResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
        
        public String getStatus() {
            return status;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
