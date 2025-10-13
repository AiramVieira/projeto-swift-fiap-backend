package com.swift.backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
    
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521:XE";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "oracle";
    
    public static Connection getConnection() throws SQLException {
        LOG.info("CONECTANDO AO BANCO");
        // Definir timeouts e propriedades para evitar travas
        Properties props = new Properties();
        props.setProperty("user", USERNAME);
        props.setProperty("password", PASSWORD);
        // Tempo máximo para estabelecer conexão (em segundos)
        System.setProperty("oracle.net.CONNECT_TIMEOUT", "10000");
        // Tempo máximo de leitura de socket (em milissegundos)
        System.setProperty("oracle.net.READ_TIMEOUT", "15000");
        return DriverManager.getConnection(URL, props);
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}

