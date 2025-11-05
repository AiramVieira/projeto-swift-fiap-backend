package com.swift.backend.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "t_fin_autenticacao")
public class Autenticacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_autenticacao")
    @SequenceGenerator(name = "seq_autenticacao", sequenceName = "seq_autenticacao", allocationSize = 1)
    @Column(name = "cd_autenticacao")
    private Integer cdAutenticacao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cd_usuario", nullable = false)
    private Usuario usuario;
    
    @Transient
    private Integer cdUsuario;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "senha", nullable = false)
    private String senha;
    
    @Column(name = "status_conta", nullable = false)
    private String statusConta;

    public Autenticacao() {
    }

    public Autenticacao(Integer cdAutenticacao, Usuario usuario, String email, String senha, String statusConta) {
        this.cdAutenticacao = cdAutenticacao;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @JsonSetter("cdUsuario")
    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
        if (cdUsuario != null && (this.usuario == null || !cdUsuario.equals(this.usuario.getCdUsuario()))) {
            this.usuario = new Usuario();
            this.usuario.setCdUsuario(cdUsuario);
        }
    }
    
    public Integer getCdUsuario() {
        if (usuario != null) {
            return usuario.getCdUsuario();
        }
        return cdUsuario;
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