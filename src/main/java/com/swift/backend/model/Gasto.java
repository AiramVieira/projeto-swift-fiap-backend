package com.swift.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_gastos")
public class Gasto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_gasto")
    private Integer cdGasto;
    
    @Column(name = "cd_usuario", nullable = false)
    private Integer cdUsuario;
    
    @Column(name = "cd_categoria", nullable = false)
    private Integer cdCategoria;
    
    @Column(name = "nm_gasto", nullable = false)
    private String nmGasto;
    
    @Column(name = "dt_gasto", nullable = false)
    private LocalDate dtGasto;
    
    @Column(name = "vl_gasto", nullable = false)
    private Double vlGasto;
    
    @Column(name = "ds_gasto")
    private String dsGasto;

    public Gasto() {
    }

    public Gasto(Integer cdGasto, Integer cdUsuario, Integer cdCategoria, String nmGasto, LocalDate dtGasto, Double vlGasto, String dsGasto) {
        this.cdGasto = cdGasto;
        this.cdUsuario = cdUsuario;
        this.cdCategoria = cdCategoria;
        this.nmGasto = nmGasto;
        this.dtGasto = dtGasto;
        this.vlGasto = vlGasto;
        this.dsGasto = dsGasto;
    }

    public Integer getCdGasto() {
        return cdGasto;
    }

    public void setCdGasto(Integer cdGasto) {
        this.cdGasto = cdGasto;
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

    public String getNmGasto() {
        return nmGasto;
    }

    public void setNmGasto(String nmGasto) {
        this.nmGasto = nmGasto;
    }

    public LocalDate getDtGasto() {
        return dtGasto;
    }

    public void setDtGasto(LocalDate dtGasto) {
        this.dtGasto = dtGasto;
    }

    public Double getVlGasto() {
        return vlGasto;
    }

    public void setVlGasto(Double vlGasto) {
        this.vlGasto = vlGasto;
    }

    public String getDsGasto() {
        return dsGasto;
    }

    public void setDsGasto(String dsGasto) {
        this.dsGasto = dsGasto;
    }
}
