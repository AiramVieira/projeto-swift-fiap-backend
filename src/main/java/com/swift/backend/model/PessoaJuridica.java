package com.swift.backend.model;

public class PessoaJuridica {
    private Integer usuarioId;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String inscricaoEstadual;
    private Boolean isento;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Integer usuarioId, String razaoSocial, String nomeFantasia, 
                          String cnpj, String inscricaoEstadual, Boolean isento) {
        this.usuarioId = usuarioId;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.isento = isento;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Boolean getIsento() {
        return isento;
    }

    public void setIsento(Boolean isento) {
        this.isento = isento;
    }
}

