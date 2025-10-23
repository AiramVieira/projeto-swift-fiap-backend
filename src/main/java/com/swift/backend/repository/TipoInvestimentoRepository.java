package com.swift.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.TipoInvestimento;

@Repository
public interface TipoInvestimentoRepository extends JpaRepository<TipoInvestimento, Integer> {
}
