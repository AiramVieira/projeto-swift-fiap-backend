package com.swift.backend.controller;

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

