package com.swift.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swift.backend.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    @Query("SELECT c FROM Categoria c WHERE c.tpCategoria = :tpCategoria")
    List<Categoria> findByTpCategoria(@Param("tpCategoria") Character tpCategoria);
}
