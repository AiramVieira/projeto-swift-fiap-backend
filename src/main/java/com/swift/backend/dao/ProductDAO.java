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
import com.swift.backend.model.Product;

public class ProductDAO {

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        
        return products;
    }

    public Optional<Product> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    public List<Product> findByCategoria(Integer categoriaId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE fk_categoria_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, categoriaId);
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        
        return products;
    }

    public List<Product> searchByNome(String nome) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE nome LIKE ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        
        return products;
    }

    public Product save(Product product) throws SQLException {
        String sql = "INSERT INTO produtos (nome, fk_categoria_id, preco, promocao, estoque, imagem, descricao, desconto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, product.getNome());
            statement.setInt(2, product.getCategoriaId());
            statement.setFloat(3, product.getPreco());
            statement.setObject(4, product.getPromocao());
            statement.setInt(5, product.getEstoque());
            statement.setString(6, product.getImagem());
            statement.setString(7, product.getDescricao());
            statement.setObject(8, product.getDesconto());
            
            statement.executeUpdate();
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            }
        }
        
        return product;
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, fk_categoria_id = ?, preco = ?, promocao = ?, estoque = ?, imagem = ?, descricao = ?, desconto = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, product.getNome());
            statement.setInt(2, product.getCategoriaId());
            statement.setFloat(3, product.getPreco());
            statement.setObject(4, product.getPromocao());
            statement.setInt(5, product.getEstoque());
            statement.setString(6, product.getImagem());
            statement.setString(7, product.getDescricao());
            statement.setObject(8, product.getDesconto());
            statement.setInt(9, product.getId());
            
            statement.executeUpdate();
        }
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setNome(rs.getString("nome"));
        product.setCategoriaId(rs.getInt("fk_categoria_id"));
        product.setPreco(rs.getFloat("preco"));
        product.setPromocao((Integer) rs.getObject("promocao"));
        product.setEstoque(rs.getInt("estoque"));
        product.setImagem(rs.getString("imagem"));
        product.setDescricao(rs.getString("descricao"));
        product.setDesconto((Integer) rs.getObject("desconto"));
        return product;
    }
}

