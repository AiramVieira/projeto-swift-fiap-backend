package com.swift.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_fin_banco_usuario")
public class BancoUsuario {
    
    @Id
    @Column(name = "cd_usuario")
    private Integer cdUsuario;
    
    @Column(name = "nr_agencia", nullable = false)
    private Integer nrAgencia;
    
    @Column(name = "nr_conta", nullable = false)
    private Long nrConta;
    
    @Column(name = "cpf", nullable = false)
    private Long cpf;
    
    @Column(name = "cd_banco", nullable = false)
    private Integer cdBanco;

    public BancoUsuario() {
    }

    public BancoUsuario(Integer cdUsuario, Integer nrAgencia, Long nrConta, Long cpf, Integer cdBanco) {
        this.cdUsuario = cdUsuario;
        this.nrAgencia = nrAgencia;
        this.nrConta = nrConta;
        this.cpf = cpf;
        this.cdBanco = cdBanco;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Integer getNrAgencia() {
        return nrAgencia;
    }

    public void setNrAgencia(Integer nrAgencia) {
        this.nrAgencia = nrAgencia;
    }

    public Long getNrConta() {
        return nrConta;
    }

    public void setNrConta(Long nrConta) {
        this.nrConta = nrConta;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Integer getCdBanco() {
        return cdBanco;
    }

    public void setCdBanco(Integer cdBanco) {
        this.cdBanco = cdBanco;
    }
}
