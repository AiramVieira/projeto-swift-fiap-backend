package com.swift.backend.model;

public class Product {
    
    private Integer id;
    private String nome;
    private Integer categoriaId;
    private Float preco;
    private Integer promocao;
    private Integer estoque;
    private String imagem;
    private String descricao;
    private Integer desconto;

    public Product() {
    }

    public Product(Integer id, String nome, Integer categoriaId, Float preco, Integer promocao, 
                   Integer estoque, String imagem, String descricao, Integer desconto) {
        this.id = id;
        this.nome = nome;
        this.categoriaId = categoriaId;
        this.preco = preco;
        this.promocao = promocao;
        this.estoque = estoque;
        this.imagem = imagem;
        this.descricao = descricao;
        this.desconto = desconto;
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

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Integer getPromocao() {
        return promocao;
    }

    public void setPromocao(Integer promocao) {
        this.promocao = promocao;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }
}
