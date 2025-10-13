package com.swift.backend.dao;

import java.sql.*;
import java.util.*;
import com.swift.backend.config.DatabaseConfig;
import com.swift.backend.model.Product;

public class ProductDAO {

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM produtos";
        Connection conn = DatabaseConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return products;
    }

    public Optional<Product> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        Optional<Product> result = rs.next() ? Optional.of(mapResultSet(rs)) : Optional.empty();
        
        rs.close();
        stmt.close();
        conn.close();
        
        return result;
    }

    public List<Product> findByCategoria(Integer categoriaId) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE fk_categoria_id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, categoriaId);
        ResultSet rs = stmt.executeQuery();
        
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return products;
    }

    public List<Product> searchByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE nome LIKE ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, "%" + nome + "%");
        ResultSet rs = stmt.executeQuery();
        
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(mapResultSet(rs));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return products;
    }

    public Product save(Product product) throws SQLException {
        String sql = "INSERT INTO produtos (nome, fk_categoria_id, preco, promocao, estoque, imagem, descricao, desconto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        setProductParams(stmt, product);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Object idObj = rs.getObject(1);
            if (idObj != null) {
                product.setId(((Number) idObj).intValue());
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return product;
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, fk_categoria_id = ?, preco = ?, promocao = ?, " +
                     "estoque = ?, imagem = ?, descricao = ?, desconto = ? WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        setProductParams(stmt, product);
        stmt.setInt(9, product.getId());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, id);
        boolean result = stmt.executeUpdate() > 0;
        
        stmt.close();
        conn.close();
        
        return result;
    }

    private void setProductParams(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getNome());
        stmt.setInt(2, product.getCategoriaId());
        stmt.setFloat(3, product.getPreco());
        stmt.setObject(4, product.getPromocao());
        stmt.setInt(5, product.getEstoque());
        stmt.setString(6, product.getImagem());
        stmt.setString(7, product.getDescricao());
        stmt.setObject(8, product.getDesconto());
    }

    private Product mapResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setNome(rs.getString("nome"));
        product.setCategoriaId(rs.getInt("fk_categoria_id"));
        product.setPreco(rs.getFloat("preco"));
        
        Object promocaoObj = rs.getObject("promocao");
        if (promocaoObj != null) {
            product.setPromocao(((Number) promocaoObj).intValue());
        }
        
        product.setEstoque(rs.getInt("estoque"));
        product.setImagem(rs.getString("imagem"));
        product.setDescricao(rs.getString("descricao"));
        
        Object descontoObj = rs.getObject("desconto");
        if (descontoObj != null) {
            product.setDesconto(((Number) descontoObj).intValue());
        }
        
        return product;
    }
}
