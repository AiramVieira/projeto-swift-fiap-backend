package com.swift.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Autenticacao;

@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Integer> {
}
