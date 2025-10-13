package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Endereco;

public class EnderecoDAO {

    public List<Endereco> findAll() throws SQLException {
        String sql = "SELECT * FROM endereco";
        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Endereco> enderecos = new ArrayList<>();
        while (rs.next()) {
            enderecos.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return enderecos;
    }

    public Optional<Endereco> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Endereco> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public Endereco save(Endereco endereco) throws SQLException {
        String sql = "BEGIN INSERT INTO endereco (descricao, cep, latitude, longitude) " +
                     "VALUES (?, ?, ?, ?) RETURNING id INTO ?; END;";
        Connection conn = DatabaseConfig.getConnection();
        CallableStatement stmt = conn.prepareCall(sql);
        
        stmt.setString(1, endereco.getDescricao());
        stmt.setString(2, endereco.getCep());
        stmt.setBigDecimal(3, endereco.getLatitude());
        stmt.setBigDecimal(4, endereco.getLongitude());
        stmt.registerOutParameter(5, Types.INTEGER);
        stmt.execute();
        
        Object idObj = stmt.getObject(5);
        if (idObj != null) {
            endereco.setId(((Number) idObj).intValue());
        }
        
        stmt.close();
        conn.close();
        
        return endereco;
    }

    public void update(Endereco endereco) throws SQLException {
        String sql = "UPDATE endereco SET descricao = ?, cep = ?, latitude = ?, longitude = ? WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, endereco.getDescricao());
        stmt.setString(2, endereco.getCep());
        stmt.setBigDecimal(3, endereco.getLatitude());
        stmt.setBigDecimal(4, endereco.getLongitude());
        stmt.setInt(5, endereco.getId());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    private Endereco mapResultSet(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("id"));
        endereco.setDescricao(rs.getString("descricao"));
        endereco.setCep(rs.getString("cep"));
        endereco.setLatitude(rs.getBigDecimal("latitude"));
        endereco.setLongitude(rs.getBigDecimal("longitude"));
        return endereco;
    }
}
