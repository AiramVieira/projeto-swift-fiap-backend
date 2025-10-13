package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Usuario;

public class UsuarioDAO {

    public List<Usuario> findAll() throws SQLException {
        String sql = "SELECT * FROM usuario";
        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Usuario> usuarios = new ArrayList<>();
        while (rs.next()) {
            usuarios.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return usuarios;
    }

    public Optional<Usuario> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Usuario> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public Usuario save(Usuario usuario) throws SQLException {
        String sql = "BEGIN INSERT INTO usuario (nome, sobrenome, fk_endereco, telephone, tipo) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id INTO ?; END;";
        Connection conn = DatabaseConfig.getConnection();
        CallableStatement stmt = conn.prepareCall(sql);
        
        try {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            
            if (usuario.getEnderecoId() != null) {
                stmt.setInt(3, usuario.getEnderecoId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setString(4, usuario.getTelephone());
            stmt.setString(5, usuario.getTipo());
            stmt.registerOutParameter(6, Types.INTEGER);
            
            stmt.execute();
            
            Object idObj = stmt.getObject(6);
            if (idObj != null) {
                usuario.setId(((Number) idObj).intValue());
            }
            
        } catch (SQLException e) {
            System.err.println("Erro SQL ao inserir usuário: " + e.getMessage());
            System.err.println("SQL: " + sql);
            System.err.println("Usuário: " + usuario.getNome() + " " + usuario.getSobrenome());
            System.err.println("EnderecoId: " + usuario.getEnderecoId());
            System.err.println("Tipo: " + usuario.getTipo());
            throw e;
        } finally {
            stmt.close();
            conn.close();
        }
        
        return usuario;
    }

    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, sobrenome = ?, fk_endereco = ?, " +
                     "telephone = ?, tipo = ? WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        setUsuarioParams(stmt, usuario);
        stmt.setInt(6, usuario.getId());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        boolean result = stmt.executeUpdate() > 0;
        
        stmt.close();
        conn.close();
        
        return result;
    }

    private void setUsuarioParams(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getSobrenome());
        
        if (usuario.getEnderecoId() != null) {
            stmt.setInt(3, usuario.getEnderecoId());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }
        
        stmt.setString(4, usuario.getTelephone());
        stmt.setString(5, usuario.getTipo());
    }

    private Usuario mapResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        
        Object idObj = rs.getObject("id");
        if (idObj != null) {
            usuario.setId(((Number) idObj).intValue());
        }
        
        usuario.setNome(rs.getString("nome"));
        usuario.setSobrenome(rs.getString("sobrenome"));
        
        Object enderecoIdObj = rs.getObject("fk_endereco");
        if (enderecoIdObj != null) {
            usuario.setEnderecoId(((Number) enderecoIdObj).intValue());
        }
        
        usuario.setTelephone(rs.getString("telephone"));
        usuario.setTipo(rs.getString("tipo"));
        return usuario;
    }
}
