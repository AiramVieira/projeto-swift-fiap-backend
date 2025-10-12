package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ItemDoCarrinho;
import com.swift.backend.service.CarrinhoService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @Inject
    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @Get("/usuario/{usuarioId}")
    public HttpResponse<?> getCarrinhoByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            Optional<Carrinho> carrinho = carrinhoService.getCarrinhoByUsuarioId(usuarioId);
            
            if (carrinho.isPresent()) {
                return HttpResponse.ok(carrinho.get());
            } else {
                return HttpResponse.notFound("Carrinho não encontrado para este usuário");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post("/{usuarioId}")
    public HttpResponse<?> createCarrinho(@PathVariable Integer usuarioId) {
        try {
            Carrinho carrinho = carrinhoService.createCarrinho(usuarioId);
            return HttpResponse.status(HttpStatus.CREATED).body(carrinho);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{carrinhoId}/itens")
    public HttpResponse<List<ItemDoCarrinho>> getItensDoCarrinho(@PathVariable Integer carrinhoId) {
        try {
            List<ItemDoCarrinho> itens = carrinhoService.getItensDoCarrinho(carrinhoId);
            return HttpResponse.ok(itens);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post("/{carrinhoId}/itens/{itemId}")
    public HttpResponse<?> adicionarItem(@PathVariable Integer carrinhoId, @PathVariable Integer itemId) {
        try {
            carrinhoService.adicionarItemAoCarrinho(carrinhoId, itemId);
            return HttpResponse.ok("Item adicionado ao carrinho");
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{carrinhoId}/itens/{itemId}")
    public HttpResponse<?> removerItem(@PathVariable Integer carrinhoId, @PathVariable Integer itemId) {
        try {
            carrinhoService.removerItemDoCarrinho(carrinhoId, itemId);
            return HttpResponse.ok("Item removido do carrinho");
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}

