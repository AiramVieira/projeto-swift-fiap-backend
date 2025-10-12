package com.swift.backend.model;

public class Endereco {
    private Integer id;
    private String descricao;
    private String cep;
    private Integer latitude;
    private Integer longitude;

    public Endereco() {
    }

    public Endereco(Integer id, String descricao, String cep, Integer latitude, Integer longitude) {
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

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }
}

