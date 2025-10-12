package com.swift.backend.model;

public class ItemDoCarrinho {
    private Integer id;
    private Integer quantidade;
    private Integer produtoId;

    public ItemDoCarrinho() {
    }

    public ItemDoCarrinho(Integer id, Integer quantidade, Integer produtoId) {
        this.id = id;
        this.quantidade = quantidade;
        this.produtoId = produtoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}

