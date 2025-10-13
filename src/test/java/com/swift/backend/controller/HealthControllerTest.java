package com.swift.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HealthController - Testes Unitários")
class HealthControllerTest {

    private HealthController healthController;

    @BeforeEach
    void setUp() {
        healthController = new HealthController();
    }

    @Test
    @DisplayName("GET / - Deve retornar mensagem de boas-vindas")
    void testIndex() {
        String response = healthController.index();

        assertNotNull(response);
        assertEquals("Swift Backend API - Sistema de Loja Online", response);
    }

    @Test
    @DisplayName("GET /health - Deve retornar status UP")
    void testHealth() {
        Map<String, String> response = healthController.health();

        assertNotNull(response);
        assertEquals("UP", response.get("status"));
        assertEquals("API está funcionando", response.get("message"));
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("GET /health - Deve conter as chaves corretas")
    void testHealthKeys() {
        Map<String, String> response = healthController.health();

        assertTrue(response.containsKey("status"));
        assertTrue(response.containsKey("message"));
    }
}

