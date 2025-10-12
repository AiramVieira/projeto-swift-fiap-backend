package com.swift.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Loja;

public class LojaDAO {

    public List<Loja> findAll() throws SQLException {
        List<Loja> lojas = new ArrayList<>();
        String sql = "SELECT * FROM loja";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                Loja loja = new Loja();
                loja.setId(rs.getInt("id"));
                loja.setEnderecoId(rs.getInt("fk_endereco_id"));
                lojas.add(loja);
            }
        }
        
        return lojas;
    }

    public Optional<Loja> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM loja WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Loja loja = new Loja();
                    loja.setId(rs.getInt("id"));
                    loja.setEnderecoId(rs.getInt("fk_endereco_id"));
                    return Optional.of(loja);
                }
            }
        }
        
        return Optional.empty();
    }

    public List<Integer> findProdutosIdsByLojaId(Integer lojaId) throws SQLException {
        List<Integer> produtosIds = new ArrayList<>();
        String sql = "SELECT fk_produto_id FROM loja_produtos WHERE fk_loja_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, lojaId);
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    produtosIds.add(rs.getInt("fk_produto_id"));
                }
            }
        }
        
        return produtosIds;
    }

    public Loja save(Loja loja) throws SQLException {
        String sql = "INSERT INTO loja (fk_endereco_id) VALUES (?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, loja.getEnderecoId());
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    loja.setId(rs.getInt(1));
                }
            }
        }
        
        return loja;
    }
}

