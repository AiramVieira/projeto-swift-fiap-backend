package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Autenticacao;
import com.swift.backend.model.Usuario;
import com.swift.backend.repository.AutenticacaoRepository;
import com.swift.backend.repository.AutenticacaoRepositoryImpl;
import com.swift.backend.repository.UsuarioRepository;

@Service
public class AutenticacaoService {
    
    @Autowired
    private AutenticacaoRepository autenticacaoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Autenticacao> getAllAutenticacoes() {
        return autenticacaoRepository.findAll();
    }

    @Autowired
    private AutenticacaoRepositoryImpl autenticacaoRepositoryImpl;
    
    public Integer getCdUsuarioByEmailAndSenha(String email, String senha) {
        return autenticacaoRepositoryImpl.findByEmailAndSenhaAndStatusConta(email, senha).get().getUsuario().getCdUsuario();
    }

    public Autenticacao createAutenticacao(Autenticacao autenticacao) {
        validateAutenticacao(autenticacao);
        
        if (autenticacao.getUsuario() == null || autenticacao.getUsuario().getCdUsuario() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório para criar autenticação");
        }
        
        Optional<Usuario> usuario = usuarioRepository.findById(autenticacao.getUsuario().getCdUsuario());
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com código: " + autenticacao.getUsuario().getCdUsuario());
        }
        
        autenticacao.setUsuario(usuario.get());
        Autenticacao saved = autenticacaoRepository.save(autenticacao);
        
        usuario.get().setCdAutenticacao(saved.getCdAutenticacao());
        usuarioRepository.save(usuario.get());
        
        return saved;
    }

    public void updateAutenticacao(Integer id, Autenticacao autenticacao) {
        Optional<Autenticacao> existing = autenticacaoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Autenticação não encontrada com id: " + id);
        }
        
        autenticacao.setCdAutenticacao(id);
        validateAutenticacao(autenticacao);
        
        if (autenticacao.getUsuario() != null && autenticacao.getUsuario().getCdUsuario() != null) {
            Optional<Usuario> usuario = usuarioRepository.findById(autenticacao.getUsuario().getCdUsuario());
            if (usuario.isEmpty()) {
                throw new IllegalArgumentException("Usuário não encontrado com código: " + autenticacao.getUsuario().getCdUsuario());
            }
            autenticacao.setUsuario(usuario.get());
        } else {
            autenticacao.setUsuario(existing.get().getUsuario());
        }
        
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
    }
}
