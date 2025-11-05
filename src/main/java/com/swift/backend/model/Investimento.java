package com.swift.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_investimento")
public class Investimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_investimento")
    @SequenceGenerator(name = "seq_investimento", sequenceName = "seq_investimento", allocationSize = 1)
    @Column(name = "cd_investimento")
    private Integer cdInvestimento;
    
    @Column(name = "cd_usuario", nullable = false)
    private Integer cdUsuario;
    
    @Column(name = "cd_tipo", nullable = false)
    private Integer cdTipo;
    
    @Column(name = "vl_investimento", nullable = false, columnDefinition = "NUMBER(12,2)")
    private Double vlInvestimento;
    
    @Column(name = "dt_investimento", nullable = false)
    private LocalDate dtInvestimento;
    
    @Column(name = "rentabilidade_estimada", columnDefinition = "NUMBER(12,2)")
    private Double rentabilidadeEstimada;
    
    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    public Investimento() {
    }

    public Investimento(Integer cdInvestimento, Integer cdUsuario, Integer cdTipo, Double vlInvestimento, LocalDate dtInvestimento, Double rentabilidadeEstimada, LocalDate dtVencimento) {
        this.cdInvestimento = cdInvestimento;
        this.cdUsuario = cdUsuario;
        this.cdTipo = cdTipo;
        this.vlInvestimento = vlInvestimento;
        this.dtInvestimento = dtInvestimento;
        this.rentabilidadeEstimada = rentabilidadeEstimada;
        this.dtVencimento = dtVencimento;
    }

    public Integer getCdInvestimento() {
        return cdInvestimento;
    }

    public void setCdInvestimento(Integer cdInvestimento) {
        this.cdInvestimento = cdInvestimento;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Integer getCdTipo() {
        return cdTipo;
    }

    public void setCdTipo(Integer cdTipo) {
        this.cdTipo = cdTipo;
    }

    public Double getVlInvestimento() {
        return vlInvestimento;
    }

    public void setVlInvestimento(Double vlInvestimento) {
        this.vlInvestimento = vlInvestimento;
    }

    public LocalDate getDtInvestimento() {
        return dtInvestimento;
    }

    public void setDtInvestimento(LocalDate dtInvestimento) {
        this.dtInvestimento = dtInvestimento;
    }

    public Double getRentabilidadeEstimada() {
        return rentabilidadeEstimada;
    }

    public void setRentabilidadeEstimada(Double rentabilidadeEstimada) {
        this.rentabilidadeEstimada = rentabilidadeEstimada;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }
}
