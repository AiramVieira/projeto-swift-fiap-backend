package com.swift.backend.model;

import java.time.LocalDate;

public class PessoaFisica {
    private Integer usuarioId;
    private LocalDate dataNascimento;

    public PessoaFisica() {
    }

    public PessoaFisica(Integer usuarioId, LocalDate dataNascimento) {
        this.usuarioId = usuarioId;
        this.dataNascimento = dataNascimento;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

