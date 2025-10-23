package com.swift.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_autenticacao")
public class Autenticacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_autenticacao")
    private Integer cdAutenticacao;
    
    @Column(name = "cd_usuario", nullable = false)
    private Integer cdUsuario;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "senha", nullable = false)
    private String senha;
    
    @Column(name = "status_conta", nullable = false)
    private String statusConta;

    public Autenticacao() {
    }

    public Autenticacao(Integer cdAutenticacao, Integer cdUsuario, String email, String senha, String statusConta) {
        this.cdAutenticacao = cdAutenticacao;
        this.cdUsuario = cdUsuario;
        this.email = email;
        this.senha = senha;
        this.statusConta = statusConta;
    }

    public Integer getCdAutenticacao() {
        return cdAutenticacao;
    }

    public void setCdAutenticacao(Integer cdAutenticacao) {
        this.cdAutenticacao = cdAutenticacao;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
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

    public String getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }
}