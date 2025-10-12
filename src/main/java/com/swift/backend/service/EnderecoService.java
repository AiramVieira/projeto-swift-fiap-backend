package com.swift.backend.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.EnderecoDAO;
import com.swift.backend.model.Endereco;

import jakarta.inject.Singleton;

@Singleton
public class EnderecoService {
    
    private final EnderecoDAO enderecoDAO;

    public EnderecoService() {
        this.enderecoDAO = new EnderecoDAO();
    }

    public List<Endereco> getAllEnderecos() throws SQLException {
        return enderecoDAO.findAll();
    }

    public Optional<Endereco> getEnderecoById(Integer id) throws SQLException {
        return enderecoDAO.findById(id);
    }

    public Endereco createEndereco(Endereco endereco) throws SQLException {
        validateEndereco(endereco);
        return enderecoDAO.save(endereco);
    }

    public void updateEndereco(Integer id, Endereco endereco) throws SQLException {
        Optional<Endereco> existing = enderecoDAO.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Endereço não encontrado com id: " + id);
        }
        
        endereco.setId(id);
        validateEndereco(endereco);
        enderecoDAO.update(endereco);
    }

    private void validateEndereco(Endereco endereco) {
        if (endereco.getDescricao() == null || endereco.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do endereço é obrigatória");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
    }
}

