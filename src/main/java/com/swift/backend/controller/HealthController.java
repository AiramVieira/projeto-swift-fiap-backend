package com.swift.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Swift Backend API - Sistema de Loja Online";
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP", "API está funcionando");
    }

    public static class HealthResponse {
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
