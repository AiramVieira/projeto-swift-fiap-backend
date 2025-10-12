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
import com.swift.backend.model.Categoria;

public class CategoriaDAO {

    public List<Categoria> findAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                categorias.add(mapResultSetToCategoria(rs));
            }
        }
        
        return categorias;
    }

    public Optional<Categoria> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategoria(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    public Categoria save(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (descricao) VALUES (?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, categoria.getDescricao());
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setId(rs.getInt(1));
                }
            }
        }
        
        return categoria;
    }

    public void update(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET descricao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, categoria.getDescricao());
            statement.setInt(2, categoria.getId());
            statement.executeUpdate();
        }
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Categoria mapResultSetToCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setDescricao(rs.getString("descricao"));
        return categoria;
    }
}

