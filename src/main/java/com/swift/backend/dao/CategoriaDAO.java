package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Categoria;

public class CategoriaDAO {

    public List<Categoria> findAll() throws SQLException {
        String sql = "SELECT * FROM categoria";
        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Categoria> categorias = new ArrayList<>();
        while (rs.next()) {
            categorias.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return categorias;
    }

    public Optional<Categoria> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Categoria> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public Categoria save(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (descricao) VALUES (?)";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, categoria.getDescricao());
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Object idObj = rs.getObject(1);
            if (idObj != null) {
                categoria.setId(((Number) idObj).intValue());
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return categoria;
    }

    public void update(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET descricao = ? WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, categoria.getDescricao());
        stmt.setInt(2, categoria.getId());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        boolean result = stmt.executeUpdate() > 0;
        
        stmt.close();
        conn.close();
        
        return result;
    }

    private Categoria mapResultSet(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setDescricao(rs.getString("descricao"));
        return categoria;
    }
}
