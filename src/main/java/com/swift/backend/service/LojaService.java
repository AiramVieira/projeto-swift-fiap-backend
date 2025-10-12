package com.swift.backend.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.LojaDAO;
import com.swift.backend.dao.ProductDAO;
import com.swift.backend.model.Loja;
import com.swift.backend.model.Product;

public class LojaService {
    
    private final LojaDAO lojaDAO;
    private final ProductDAO productDAO;

    public LojaService() {
        this.lojaDAO = new LojaDAO();
        this.productDAO = new ProductDAO();
    }

    public List<Loja> getAllLojas() throws SQLException {
        return lojaDAO.findAll();
    }

    public Optional<Loja> getLojaById(Integer id) throws SQLException {
        return lojaDAO.findById(id);
    }

    public List<Product> getProdutosByLojaId(Integer lojaId) throws SQLException {
        List<Integer> produtosIds = lojaDAO.findProdutosIdsByLojaId(lojaId);
        List<Product> produtos = new ArrayList<>();
        
        for (Integer produtoId : produtosIds) {
            productDAO.findById(produtoId).ifPresent(produtos::add);
        }
        
        return produtos;
    }

    public Loja createLoja(Loja loja) throws SQLException {
        if (loja.getEnderecoId() == null) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }
        return lojaDAO.save(loja);
    }
}

