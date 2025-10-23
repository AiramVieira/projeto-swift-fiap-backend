package com.swift.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_recebimentos")
public class Recebimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_recebimento")
    private Integer cdRecebimento;
    
    @Column(name = "cd_usuario", nullable = false)
    private Integer cdUsuario;
    
    @Column(name = "cd_categoria", nullable = false)
    private Integer cdCategoria;
    
    @Column(name = "nm_recebimento", nullable = false)
    private String nmRecebimento;
    
    @Column(name = "dt_recebimento", nullable = false)
    private LocalDate dtRecebimento;
    
    @Column(name = "vl_recebimento", nullable = false)
    private Double vlRecebimento;
    
    @Column(name = "ds_recebimento")
    private String dsRecebimento;

    public Recebimento() {
    }

    public Recebimento(Integer cdRecebimento, Integer cdUsuario, Integer cdCategoria, String nmRecebimento, LocalDate dtRecebimento, Double vlRecebimento, String dsRecebimento) {
        this.cdRecebimento = cdRecebimento;
        this.cdUsuario = cdUsuario;
        this.cdCategoria = cdCategoria;
        this.nmRecebimento = nmRecebimento;
        this.dtRecebimento = dtRecebimento;
        this.vlRecebimento = vlRecebimento;
        this.dsRecebimento = dsRecebimento;
    }

    public Integer getCdRecebimento() {
        return cdRecebimento;
    }

    public void setCdRecebimento(Integer cdRecebimento) {
        this.cdRecebimento = cdRecebimento;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Integer getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(Integer cdCategoria) {
        this.cdCategoria = cdCategoria;
    }

    public String getNmRecebimento() {
        return nmRecebimento;
    }

    public void setNmRecebimento(String nmRecebimento) {
        this.nmRecebimento = nmRecebimento;
    }

    public LocalDate getDtRecebimento() {
        return dtRecebimento;
    }

    public void setDtRecebimento(LocalDate dtRecebimento) {
        this.dtRecebimento = dtRecebimento;
    }

    public Double getVlRecebimento() {
        return vlRecebimento;
    }

    public void setVlRecebimento(Double vlRecebimento) {
        this.vlRecebimento = vlRecebimento;
    }

    public String getDsRecebimento() {
        return dsRecebimento;
    }

    public void setDsRecebimento(String dsRecebimento) {
        this.dsRecebimento = dsRecebimento;
    }
}
