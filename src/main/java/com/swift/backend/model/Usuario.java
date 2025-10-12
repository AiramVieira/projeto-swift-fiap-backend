package com.swift.backend.model;

public class Usuario {
    private Integer id;
    private String nome;
    private String sobrenome;
    private Integer enderecoId;
    private String telephone;
    private String tipo;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String sobrenome, Integer enderecoId, String telephone, String tipo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.enderecoId = enderecoId;
        this.telephone = telephone;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

