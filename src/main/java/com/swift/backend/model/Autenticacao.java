package com.swift.backend.model;

public class Autenticacao {
    private Integer id;
    private String email;
    private String senha;
    private Integer usuarioId;

    public Autenticacao() {
    }

    public Autenticacao(Integer id, String email, String senha, Integer usuarioId) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}

