package com.swift.backend.model;

import java.time.LocalDateTime;

public class VendaComum {
    private Integer id;
    private LocalDateTime dataVenda;
    private Integer usuarioId;

    public VendaComum() {
    }

    public VendaComum(Integer id, LocalDateTime dataVenda, Integer usuarioId) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}

