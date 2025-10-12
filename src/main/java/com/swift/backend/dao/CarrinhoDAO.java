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
import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ItemDoCarrinho;

public class CarrinhoDAO {

    public Optional<Carrinho> findByUsuarioId(Integer usuarioId) throws SQLException {
        String sql = "SELECT * FROM carrinho WHERE fk_usuario_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, usuarioId);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Carrinho carrinho = new Carrinho();
                    carrinho.setId(rs.getInt("id"));
                    carrinho.setUsuarioId(rs.getInt("fk_usuario_id"));
                    return Optional.of(carrinho);
                }
            }
        }
        
        return Optional.empty();
    }

    public Carrinho save(Carrinho carrinho) throws SQLException {
        String sql = "INSERT INTO carrinho (fk_usuario_id) VALUES (?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, carrinho.getUsuarioId());
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    carrinho.setId(rs.getInt(1));
                }
            }
        }
        
        return carrinho;
    }

    public List<ItemDoCarrinho> findItensDoCarrinho(Integer carrinhoId) throws SQLException {
        List<ItemDoCarrinho> itens = new ArrayList<>();
        String sql = "SELECT ic.* FROM item_do_carrinho ic " +
                     "INNER JOIN carrinho_items ci ON ic.id = ci.fk_item_carrinho_id " +
                     "WHERE ci.fk_carrinho_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, carrinhoId);
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ItemDoCarrinho item = new ItemDoCarrinho();
                    item.setId(rs.getInt("id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setProdutoId(rs.getInt("fk_produto_id"));
                    itens.add(item);
                }
            }
        }
        
        return itens;
    }

    public void adicionarItem(Integer carrinhoId, Integer itemId) throws SQLException {
        String sql = "INSERT INTO carrinho_items (fk_carrinho_id, fk_item_carrinho_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, carrinhoId);
            statement.setInt(2, itemId);
            statement.executeUpdate();
        }
    }

    public void removerItem(Integer carrinhoId, Integer itemId) throws SQLException {
        String sql = "DELETE FROM carrinho_items WHERE fk_carrinho_id = ? AND fk_item_carrinho_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, carrinhoId);
            statement.setInt(2, itemId);
            statement.executeUpdate();
        }
    }
}

