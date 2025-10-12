package com.swift.backend.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.dao.UsuarioDAO;
import com.swift.backend.model.Usuario;

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        return usuarioDAO.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) throws SQLException {
        return usuarioDAO.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) throws SQLException {
        validateUsuario(usuario);
        return usuarioDAO.save(usuario);
    }

    public void updateUsuario(Integer id, Usuario usuario) throws SQLException {
        Optional<Usuario> existing = usuarioDAO.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com id: " + id);
        }
        
        usuario.setId(id);
        validateUsuario(usuario);
        usuarioDAO.update(usuario);
    }

    public boolean deleteUsuario(Integer id) throws SQLException {
        return usuarioDAO.delete(id);
    }

    private void validateUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (usuario.getSobrenome() == null || usuario.getSobrenome().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome é obrigatório");
        }
        if (usuario.getTipo() == null || (!usuario.getTipo().equals("PF") && !usuario.getTipo().equals("PJ"))) {
            throw new IllegalArgumentException("Tipo deve ser PF ou PJ");
        }
    }
}

