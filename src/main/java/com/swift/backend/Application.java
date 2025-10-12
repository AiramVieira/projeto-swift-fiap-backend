package com.swift.backend;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("\n==============================================");
        LOG.info("Swift Backend API está iniciando...");
        LOG.info("==============================================");
        
        Micronaut.run(Application.class, args);
        
        LOG.info("\n==============================================");
        LOG.info("Swift Backend API está rodando na porta 8080");
        LOG.info("==============================================");
        LOG.info("\nEndpoints disponíveis:");
        LOG.info("\n--- Produtos ---");
        LOG.info("GET    /api/products");
        LOG.info("GET    /api/products/{id}");
        LOG.info("GET    /api/products/categoria/{categoriaId}");
        LOG.info("GET    /api/products/search?nome=xxx");
        LOG.info("POST   /api/products");
        LOG.info("PUT    /api/products/{id}");
        LOG.info("DELETE /api/products/{id}");
        
        LOG.info("\n--- Categorias ---");
        LOG.info("GET    /api/categorias");
        LOG.info("GET    /api/categorias/{id}");
        LOG.info("POST   /api/categorias");
        LOG.info("PUT    /api/categorias/{id}");
        LOG.info("DELETE /api/categorias/{id}");
        
        LOG.info("\n--- Usuários ---");
        LOG.info("GET    /api/usuarios");
        LOG.info("GET    /api/usuarios/{id}");
        LOG.info("POST   /api/usuarios");
        LOG.info("PUT    /api/usuarios/{id}");
        LOG.info("DELETE /api/usuarios/{id}");
        
        LOG.info("\n--- Carrinho ---");
        LOG.info("GET    /api/carrinho/usuario/{usuarioId}");
        LOG.info("POST   /api/carrinho/{usuarioId}");
        LOG.info("GET    /api/carrinho/{carrinhoId}/itens");
        LOG.info("POST   /api/carrinho/{carrinhoId}/itens/{itemId}");
        LOG.info("DELETE /api/carrinho/{carrinhoId}/itens/{itemId}");
        
        LOG.info("\n--- Lojas ---");
        LOG.info("GET    /api/lojas");
        LOG.info("GET    /api/lojas/{id}");
        LOG.info("GET    /api/lojas/{id}/produtos");
        LOG.info("POST   /api/lojas");
        
        LOG.info("\n--- Endereços ---");
        LOG.info("GET    /api/enderecos");
        LOG.info("GET    /api/enderecos/{id}");
        LOG.info("POST   /api/enderecos");
        LOG.info("PUT    /api/enderecos/{id}");
        LOG.info("\n==============================================\n");
    }
}

