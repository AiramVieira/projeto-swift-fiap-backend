package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Loja;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

public class LojaDAO {

    public List<Loja> findAll() throws SQLException {
        String sql = "SELECT * FROM loja";
        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Loja> lojas = new ArrayList<>();
        while (rs.next()) {
            lojas.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return lojas;
    }

    public Optional<Loja> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM loja WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Loja> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public List<Integer> findProdutosIdsByLojaId(Integer lojaId) throws SQLException {
        String sql = "SELECT fk_produto_id FROM loja_produtos WHERE fk_loja_id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, lojaId);
        ResultSet rs = stmt.executeQuery();
        
        List<Integer> ids = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getInt("fk_produto_id"));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return ids;
    }

    public Loja save(Loja loja) throws SQLException {
        String sql = "INSERT INTO loja (fk_endereco_id) VALUES (?) RETURNING id INTO ?";
        Connection conn = DatabaseConfig.getConnection();
        OraclePreparedStatement stmt = (OraclePreparedStatement) conn.prepareStatement(sql);
        
        stmt.setInt(1, loja.getEnderecoId());
        stmt.registerReturnParameter(2, OracleTypes.NUMBER);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getReturnResultSet();
        if (rs.next()) {
            Object idObj = rs.getObject(1);
            if (idObj != null) {
                loja.setId(((Number) idObj).intValue());
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return loja;
    }

    private Loja mapResultSet(ResultSet rs) throws SQLException {
        Loja loja = new Loja();
        loja.setId(rs.getInt("id"));
        loja.setEnderecoId(rs.getInt("fk_endereco_id"));
        return loja;
    }
}
