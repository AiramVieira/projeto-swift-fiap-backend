package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Autenticacao;
import com.swift.backend.repository.AutenticacaoRepository;

@Service
public class AutenticacaoService {
    
    @Autowired
    private AutenticacaoRepository autenticacaoRepository;

    public List<Autenticacao> getAllAutenticacoes() {
        return autenticacaoRepository.findAll();
    }

    public Optional<Autenticacao> getAutenticacaoById(Integer id) {
        return autenticacaoRepository.findById(id);
    }

    public Autenticacao createAutenticacao(Autenticacao autenticacao) {
        validateAutenticacao(autenticacao);
        return autenticacaoRepository.save(autenticacao);
    }

    public void updateAutenticacao(Integer id, Autenticacao autenticacao) {
        Optional<Autenticacao> existing = autenticacaoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Autenticação não encontrada com id: " + id);
        }
        
        autenticacao.setCdAutenticacao(id);
        validateAutenticacao(autenticacao);
        autenticacaoRepository.save(autenticacao);
    }

    public boolean deleteAutenticacao(Integer id) {
        if (autenticacaoRepository.existsById(id)) {
            autenticacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateAutenticacao(Autenticacao autenticacao) {
        if (autenticacao.getEmail() == null || autenticacao.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (autenticacao.getSenha() == null || autenticacao.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (autenticacao.getStatusConta() == null || autenticacao.getStatusConta().trim().isEmpty()) {
            throw new IllegalArgumentException("Status da conta é obrigatório");
        }
        if (autenticacao.getCdUsuario() == null) {
            throw new IllegalArgumentException("Código do usuário é obrigatório");
        }
    }
}
