package com.swift.backend.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.CategoriaDAO;
import com.swift.backend.model.Categoria;

public class CategoriaService {
    
    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public List<Categoria> getAllCategorias() throws SQLException {
        return categoriaDAO.findAll();
    }

    public Optional<Categoria> getCategoriaById(Integer id) throws SQLException {
        return categoriaDAO.findById(id);
    }

    public Categoria createCategoria(Categoria categoria) throws SQLException {
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da categoria é obrigatória");
        }
        return categoriaDAO.save(categoria);
    }

    public void updateCategoria(Integer id, Categoria categoria) throws SQLException {
        Optional<Categoria> existing = categoriaDAO.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada com id: " + id);
        }
        
        categoria.setId(id);
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da categoria é obrigatória");
        }
        categoriaDAO.update(categoria);
    }

    public boolean deleteCategoria(Integer id) throws SQLException {
        return categoriaDAO.delete(id);
    }
}

