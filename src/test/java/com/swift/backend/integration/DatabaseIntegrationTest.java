package com.swift.backend.integration;

import com.swift.backend.config.DatabaseConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração com Banco de Dados")
@EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
class DatabaseIntegrationTest {

    @Test
    @DisplayName("Deve executar query SELECT simples")
    void testSimpleSelect() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT 1 FROM DUAL");
            
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConfig.closeConnection(conn);
        }
    }

    @Test
    @DisplayName("Deve executar query com PreparedStatement")
    void testPreparedStatement() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement("SELECT 1 FROM DUAL WHERE 1 = ?");
            pstmt.setInt(1, 1);
            rs = pstmt.executeQuery();
            
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            DatabaseConfig.closeConnection(conn);
        }
    }

    @Test
    @DisplayName("Deve verificar timeout de conexão")
    void testConnectionTimeout() throws SQLException {
        Connection conn = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            assertTrue(conn.isValid(10));
            assertFalse(conn.isClosed());
        } finally {
            DatabaseConfig.closeConnection(conn);
        }
    }
}

