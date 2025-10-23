package com.swift.backend.controller;

<<<<<<< HEAD
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
=======
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Map;

@Controller
public class HealthController {

    @Get("/")
    public String index() {
        return "Swift Backend API - Sistema de Loja Online";
    }

    @Get("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "message", "API está funcionando"
        );
    }
}

>>>>>>> 64a77131b5ede24b0fc69f28b922c7d231c94237
