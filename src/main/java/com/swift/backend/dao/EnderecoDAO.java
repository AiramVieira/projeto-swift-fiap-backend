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
import com.swift.backend.model.Endereco;

public class EnderecoDAO {

    public List<Endereco> findAll() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                enderecos.add(mapResultSetToEndereco(rs));
            }
        }
        
        return enderecos;
    }

    public Optional<Endereco> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEndereco(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    public Endereco save(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (descricao, cep, latitude, longitude) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, endereco.getDescricao());
            statement.setString(2, endereco.getCep());
            statement.setObject(3, endereco.getLatitude());
            statement.setObject(4, endereco.getLongitude());
            
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getInt(1));
                }
            }
        }
        
        return endereco;
    }

    public void update(Endereco endereco) throws SQLException {
        String sql = "UPDATE endereco SET descricao = ?, cep = ?, latitude = ?, longitude = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, endereco.getDescricao());
            statement.setString(2, endereco.getCep());
            statement.setObject(3, endereco.getLatitude());
            statement.setObject(4, endereco.getLongitude());
            statement.setInt(5, endereco.getId());
            
            statement.executeUpdate();
        }
    }

    private Endereco mapResultSetToEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("id"));
        endereco.setDescricao(rs.getString("descricao"));
        endereco.setCep(rs.getString("cep"));
        endereco.setLatitude((Integer) rs.getObject("latitude"));
        endereco.setLongitude((Integer) rs.getObject("longitude"));
        return endereco;
    }
}

