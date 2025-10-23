package com.swift.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Investimento;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {
    
    @Query("SELECT i FROM Investimento i WHERE i.cdUsuario = :cdUsuario")
    List<Investimento> findByCdUsuario(@Param("cdUsuario") Integer cdUsuario);
    
    @Query("SELECT i FROM Investimento i WHERE i.cdTipo = :cdTipo")
    List<Investimento> findByCdTipo(@Param("cdTipo") Integer cdTipo);
}
