package com.swift.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {
    
    @Query("SELECT g FROM Gasto g WHERE g.cdUsuario = :cdUsuario")
    List<Gasto> findByCdUsuario(@Param("cdUsuario") Integer cdUsuario);
    
    @Query("SELECT g FROM Gasto g WHERE g.cdCategoria = :cdCategoria")
    List<Gasto> findByCdCategoria(@Param("cdCategoria") Integer cdCategoria);
}
