package com.swift.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.BancoUsuario;

@Repository
public interface BancoUsuarioRepository extends JpaRepository<BancoUsuario, Integer> {
}
