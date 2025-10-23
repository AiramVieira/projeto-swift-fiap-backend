package com.swift.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Recebimento;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Integer> {
    
    @Query("SELECT r FROM Recebimento r WHERE r.cdUsuario = :cdUsuario")
    List<Recebimento> findByCdUsuario(@Param("cdUsuario") Integer cdUsuario);
    
    @Query("SELECT r FROM Recebimento r WHERE r.cdCategoria = :cdCategoria")
    List<Recebimento> findByCdCategoria(@Param("cdCategoria") Integer cdCategoria);
}
