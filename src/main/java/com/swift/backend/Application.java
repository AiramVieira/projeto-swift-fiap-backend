package com.swift.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("\n==============================================");
        System.out.println("Swift Backend API está rodando na porta 8080");
        System.out.println("==============================================");
        System.out.println("\nEndpoints disponíveis:");
        
        System.out.println("\n--- Usuários ---");
        System.out.println("GET    /api/usuarios");
        System.out.println("GET    /api/usuarios/{id}");
        System.out.println("POST   /api/usuarios");
        System.out.println("PUT    /api/usuarios/{id}");
        System.out.println("DELETE /api/usuarios/{id}");
        
        System.out.println("\n--- Autenticação ---");
        System.out.println("GET    /api/autenticacao");
        System.out.println("GET    /api/autenticacao/{id}");
        System.out.println("POST   /api/autenticacao");
        System.out.println("PUT    /api/autenticacao/{id}");
        System.out.println("DELETE /api/autenticacao/{id}");
        
        System.out.println("\n--- Categorias ---");
        System.out.println("GET    /api/categorias");
        System.out.println("GET    /api/categorias/{id}");
        System.out.println("POST   /api/categorias");
        System.out.println("PUT    /api/categorias/{id}");
        System.out.println("DELETE /api/categorias/{id}");
        
        System.out.println("\n--- Gastos ---");
        System.out.println("GET    /api/gastos");
        System.out.println("GET    /api/gastos/{id}");
        System.out.println("GET    /api/gastos/usuario/{cdUsuario}");
        System.out.println("GET    /api/gastos/categoria/{cdCategoria}");
        System.out.println("POST   /api/gastos");
        System.out.println("PUT    /api/gastos/{id}");
        System.out.println("DELETE /api/gastos/{id}");
        
        System.out.println("\n--- Recebimentos ---");
        System.out.println("GET    /api/recebimentos");
        System.out.println("GET    /api/recebimentos/{id}");
        System.out.println("GET    /api/recebimentos/usuario/{cdUsuario}");
        System.out.println("GET    /api/recebimentos/categoria/{cdCategoria}");
        System.out.println("POST   /api/recebimentos");
        System.out.println("PUT    /api/recebimentos/{id}");
        System.out.println("DELETE /api/recebimentos/{id}");
        
        System.out.println("\n--- Investimentos ---");
        System.out.println("GET    /api/investimentos");
        System.out.println("GET    /api/investimentos/{id}");
        System.out.println("GET    /api/investimentos/usuario/{cdUsuario}");
        System.out.println("GET    /api/investimentos/tipo/{cdTipo}");
        System.out.println("POST   /api/investimentos");
        System.out.println("PUT    /api/investimentos/{id}");
        System.out.println("DELETE /api/investimentos/{id}");
        System.out.println("\n==============================================\n");
    }
}