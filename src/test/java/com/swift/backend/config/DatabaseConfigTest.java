package com.swift.backend.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DatabaseConfig - Testes de Conexão")
class DatabaseConfigTest {

    @Test
    @DisplayName("Deve conectar com o banco de dados com sucesso")
    @EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
    void testGetConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            
            assertNotNull(conn);
            assertFalse(conn.isClosed());
            assertTrue(conn.isValid(5));
        } finally {
            DatabaseConfig.closeConnection(conn);
        }
    }

    @Test
    @DisplayName("Deve fechar conexão corretamente")
    @EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
    void testCloseConnection() throws SQLException {
        Connection conn = DatabaseConfig.getConnection();
        assertNotNull(conn);
        assertFalse(conn.isClosed());
        
        DatabaseConfig.closeConnection(conn);
        
        assertTrue(conn.isClosed());
    }

    @Test
    @DisplayName("Deve lidar com conexão nula ao fechar")
    void testCloseNullConnection() {
        assertDoesNotThrow(() -> DatabaseConfig.closeConnection(null));
    }

    @Test
    @DisplayName("Deve suportar múltiplas conexões simultâneas")
    @EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
    void testMultipleConnections() throws SQLException {
        Connection conn1 = null;
        Connection conn2 = null;
        Connection conn3 = null;
        
        try {
            conn1 = DatabaseConfig.getConnection();
            conn2 = DatabaseConfig.getConnection();
            conn3 = DatabaseConfig.getConnection();
            
            assertNotNull(conn1);
            assertNotNull(conn2);
            assertNotNull(conn3);
            
            assertNotEquals(conn1, conn2);
            assertNotEquals(conn2, conn3);
            assertNotEquals(conn1, conn3);
            
            assertTrue(conn1.isValid(5));
            assertTrue(conn2.isValid(5));
            assertTrue(conn3.isValid(5));
        } finally {
            DatabaseConfig.closeConnection(conn1);
            DatabaseConfig.closeConnection(conn2);
            DatabaseConfig.closeConnection(conn3);
        }
    }
}

