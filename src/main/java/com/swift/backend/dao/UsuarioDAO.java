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
import com.swift.backend.model.Usuario;

public class UsuarioDAO {

    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
        }
        
        return usuarios;
    }

    public Optional<Usuario> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    public Usuario save(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, sobrenome, fk_endereco, telephone, tipo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSobrenome());
            statement.setInt(3, usuario.getEnderecoId());
            statement.setString(4, usuario.getTelephone());
            statement.setString(5, usuario.getTipo());
            
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        }
        
        return usuario;
    }

    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, fk_endereco = ?, telephone = ?, tipo = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSobrenome());
            statement.setInt(3, usuario.getEnderecoId());
            statement.setString(4, usuario.getTelephone());
            statement.setString(5, usuario.getTipo());
            statement.setInt(6, usuario.getId());
            
            statement.executeUpdate();
        }
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSobrenome(rs.getString("sobrenome"));
        usuario.setEnderecoId(rs.getInt("fk_endereco"));
        usuario.setTelephone(rs.getString("telephone"));
        usuario.setTipo(rs.getString("tipo"));
        return usuario;
    }
}

