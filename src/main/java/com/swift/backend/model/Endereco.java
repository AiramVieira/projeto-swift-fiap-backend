package com.swift.backend.model;

import java.math.BigDecimal;

public class Endereco {
    private Integer id;
    private String descricao;
    private String cep;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public Endereco() {
    }

    public Endereco(Integer id, String descricao, String cep, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.descricao = descricao;
        this.cep = cep;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}

