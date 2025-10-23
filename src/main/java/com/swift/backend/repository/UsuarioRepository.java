package com.swift.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query("SELECT u FROM Usuario u WHERE u.cdAutenticacao = :cdAutenticacao")
    Optional<Usuario> findByCdAutenticacao(@Param("cdAutenticacao") Integer cdAutenticacao);
}
