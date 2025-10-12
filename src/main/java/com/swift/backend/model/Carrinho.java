package com.swift.backend.model;

public class Carrinho {
    private Integer id;
    private Integer usuarioId;

    public Carrinho() {
    }

    public Carrinho(Integer id, Integer usuarioId) {
        this.id = id;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}

