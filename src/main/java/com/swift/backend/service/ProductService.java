package com.swift.backend.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.ProductDAO;
import com.swift.backend.model.Product;

import jakarta.inject.Singleton;

@Singleton
public class ProductService {
    
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.findAll();
    }

    public Optional<Product> getProductById(Integer id) throws SQLException {
        return productDAO.findById(id);
    }

    public List<Product> getProductsByCategoria(Integer categoriaId) throws SQLException {
        return productDAO.findByCategoria(categoriaId);
    }

    public List<Product> searchProducts(String nome) throws SQLException {
        return productDAO.searchByNome(nome);
    }

    public Product createProduct(Product product) throws SQLException {
        validateProduct(product);
        return productDAO.save(product);
    }

    public void updateProduct(Integer id, Product product) throws SQLException {
        Optional<Product> existing = productDAO.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Produto não encontrado com id: " + id);
        }
        
        product.setId(id);
        validateProduct(product);
        productDAO.update(product);
    }

    public boolean deleteProduct(Integer id) throws SQLException {
        return productDAO.delete(id);
    }

    private void validateProduct(Product product) {
        if (product.getNome() == null || product.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (product.getPreco() == null || product.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (product.getCategoriaId() == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
    }
}

