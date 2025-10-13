package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ItemDoCarrinho;

public class CarrinhoDAO {

    public Optional<Carrinho> findByUsuarioId(Integer usuarioId) throws SQLException {
        String sql = "SELECT * FROM carrinho WHERE fk_usuario_id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Carrinho> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public Carrinho save(Carrinho carrinho) throws SQLException {
        String sql = "INSERT INTO carrinho (fk_usuario_id) VALUES (?)";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setInt(1, carrinho.getUsuarioId());
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Object idObj = rs.getObject(1);
            if (idObj != null) {
                carrinho.setId(((Number) idObj).intValue());
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return carrinho;
    }

    public List<ItemDoCarrinho> findItensDoCarrinho(Integer carrinhoId) throws SQLException {
        String sql = "SELECT ic.* FROM item_do_carrinho ic " +
                     "INNER JOIN carrinho_items ci ON ic.id = ci.fk_item_carrinho_id " +
                     "WHERE ci.fk_carrinho_id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, carrinhoId);
        ResultSet rs = stmt.executeQuery();
        
        List<ItemDoCarrinho> itens = new ArrayList<>();
        while (rs.next()) {
            itens.add(mapItemResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return itens;
    }

    public void adicionarItem(Integer carrinhoId, Integer itemId) throws SQLException {
        String sql = "INSERT INTO carrinho_items (fk_carrinho_id, fk_item_carrinho_id) VALUES (?, ?)";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, carrinhoId);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    public void removerItem(Integer carrinhoId, Integer itemId) throws SQLException {
        String sql = "DELETE FROM carrinho_items WHERE fk_carrinho_id = ? AND fk_item_carrinho_id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, carrinhoId);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    private Carrinho mapResultSet(ResultSet rs) throws SQLException {
        Carrinho carrinho = new Carrinho();
        carrinho.setId(rs.getInt("id"));
        carrinho.setUsuarioId(rs.getInt("fk_usuario_id"));
        return carrinho;
    }

    private ItemDoCarrinho mapItemResultSet(ResultSet rs) throws SQLException {
        ItemDoCarrinho item = new ItemDoCarrinho();
        item.setId(rs.getInt("id"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setProdutoId(rs.getInt("fk_produto_id"));
        return item;
    }
}
