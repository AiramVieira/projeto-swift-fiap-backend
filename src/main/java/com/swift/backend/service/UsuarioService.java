package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Usuario;
import com.swift.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios()  {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id)  {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario)  {
        validateUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public void updateUsuario(Integer id, Usuario usuario)  {
        Optional<Usuario> existing = usuarioRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com id: " + id);
        }
        
        usuario.setCdUsuario(id);
        validateUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    public boolean deleteUsuario(Integer id)  {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario.getNmUsuario() == null || usuario.getNmUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (usuario.getDtNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }
        if (usuario.getVlSaldo() == null) {
            usuario.setVlSaldo(0.0);
        }
    }
}

