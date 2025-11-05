package com.swift.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_tipos_investimento")
public class TipoInvestimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_investimento")
    @SequenceGenerator(name = "seq_tipo_investimento", sequenceName = "seq_tipo_investimento", allocationSize = 1)
    @Column(name = "cd_tipo")
    private Integer cdTipo;
    
    @Column(name = "risco", nullable = false)
    private Character risco;
    
    @Column(name = "nm_tipo", nullable = false)
    private String nmTipo;

    public TipoInvestimento() {
    }

    public TipoInvestimento(Integer cdTipo, Character risco, String nmTipo) {
        this.cdTipo = cdTipo;
        this.risco = risco;
        this.nmTipo = nmTipo;
    }

    public Integer getCdTipo() {
        return cdTipo;
    }

    public void setCdTipo(Integer cdTipo) {
        this.cdTipo = cdTipo;
    }

    public Character getRisco() {
        return risco;
    }

    public void setRisco(Character risco) {
        this.risco = risco;
    }

    public String getNmTipo() {
        return nmTipo;
    }

    public void setNmTipo(String nmTipo) {
        this.nmTipo = nmTipo;
    }
}
