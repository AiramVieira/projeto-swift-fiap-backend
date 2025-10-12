package com.swift.backend.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.CarrinhoDAO;
import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ItemDoCarrinho;

public class CarrinhoService {
    
    private final CarrinhoDAO carrinhoDAO;

    public CarrinhoService() {
        this.carrinhoDAO = new CarrinhoDAO();
    }

    public Optional<Carrinho> getCarrinhoByUsuarioId(Integer usuarioId) throws SQLException {
        return carrinhoDAO.findByUsuarioId(usuarioId);
    }

    public Carrinho createCarrinho(Integer usuarioId) throws SQLException {
        Optional<Carrinho> existing = carrinhoDAO.findByUsuarioId(usuarioId);
        if (existing.isPresent()) {
            return existing.get();
        }
        
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuarioId(usuarioId);
        return carrinhoDAO.save(carrinho);
    }

    public List<ItemDoCarrinho> getItensDoCarrinho(Integer carrinhoId) throws SQLException {
        return carrinhoDAO.findItensDoCarrinho(carrinhoId);
    }

    public void adicionarItemAoCarrinho(Integer carrinhoId, Integer itemId) throws SQLException {
        carrinhoDAO.adicionarItem(carrinhoId, itemId);
    }

    public void removerItemDoCarrinho(Integer carrinhoId, Integer itemId) throws SQLException {
        carrinhoDAO.removerItem(carrinhoId, itemId);
    }
}

