package com.swift.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_categoria")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria")
    @SequenceGenerator(name = "seq_categoria", sequenceName = "seq_categoria", allocationSize = 1)
    @Column(name = "cd_categoria")
    private Integer cdCategoria;
    
    @Column(name = "nm_categoria", nullable = false)
    private String nmCategoria;
    
    @Column(name = "tp_categoria", nullable = false)
    private Character tpCategoria;

    public Categoria() {
    }

    public Categoria(Integer cdCategoria, String nmCategoria, Character tpCategoria) {
        this.cdCategoria = cdCategoria;
        this.nmCategoria = nmCategoria;
        this.tpCategoria = tpCategoria;
    }

    public Integer getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(Integer cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public String getNmCategoria() {
        return nmCategoria;
    }

    public void setNmCategoria(String nmCategoria) {
        this.nmCategoria = nmCategoria;
    }

    public Character getTpCategoria() {
        return tpCategoria;
    }

    public void setTpCategoria(Character tpCategoria) {
        this.tpCategoria = tpCategoria;
    }
}

