package com.swift.backend.model;

public class Loja {
    private Integer id;
    private Integer enderecoId;

    public Loja() {
    }

    public Loja(Integer id, Integer enderecoId) {
        this.id = id;
        this.enderecoId = enderecoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
}

