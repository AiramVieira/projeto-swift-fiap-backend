package com.swift.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario")
    private Integer cdUsuario;
    
    @Column(name = "cd_autenticacao")
    private Integer cdAutenticacao;
    
    @Column(name = "nm_usuario", nullable = false)
    private String nmUsuario;
    
    @Column(name = "dt_nascimento", nullable = false)
    private LocalDate dtNascimento;
    
    @Column(name = "nr_telefone")
    private String nrTelefone;
    
    @Column(name = "ativo")
    private Character ativo;
    
    @Column(name = "vl_saldo", nullable = false)
    private Double vlSaldo;

    public Usuario() {
    }

    public Usuario(Integer cdUsuario, Integer cdAutenticacao, String nmUsuario, LocalDate dtNascimento, String nrTelefone, Character ativo, Double vlSaldo) {
        this.cdUsuario = cdUsuario;
        this.cdAutenticacao = cdAutenticacao;
        this.nmUsuario = nmUsuario;
        this.dtNascimento = dtNascimento;
        this.nrTelefone = nrTelefone;
        this.ativo = ativo;
        this.vlSaldo = vlSaldo;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Integer getCdAutenticacao() {
        return cdAutenticacao;
    }

    public void setCdAutenticacao(Integer cdAutenticacao) {
        this.cdAutenticacao = cdAutenticacao;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNrTelefone() {
        return nrTelefone;
    }

    public void setNrTelefone(String nrTelefone) {
        this.nrTelefone = nrTelefone;
    }

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
    }

    public Double getVlSaldo() {
        return vlSaldo;
    }

    public void setVlSaldo(Double vlSaldo) {
        this.vlSaldo = vlSaldo;
    }
}

