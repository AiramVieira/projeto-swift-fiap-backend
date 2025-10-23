package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Categoria;
import com.swift.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getCategoriaById(Integer id) {
        return categoriaRepository.findById(id);
    }

    public Categoria createCategoria(Categoria categoria) {
        if (categoria.getNmCategoria() == null || categoria.getNmCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        if (categoria.getTpCategoria() == null) {
            throw new IllegalArgumentException("Tipo da categoria é obrigatório");
        }
        return categoriaRepository.save(categoria);
    }

    public void updateCategoria(Integer id, Categoria categoria) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada com id: " + id);
        }
        
        categoria.setCdCategoria(id);
        if (categoria.getNmCategoria() == null || categoria.getNmCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }
        if (categoria.getTpCategoria() == null) {
            throw new IllegalArgumentException("Tipo da categoria é obrigatório");
        }
        categoriaRepository.save(categoria);
    }

    public boolean deleteCategoria(Integer id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

