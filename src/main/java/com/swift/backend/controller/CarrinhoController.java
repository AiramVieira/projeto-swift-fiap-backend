package com.swift.backend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Carrinho;
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.ItemDoCarrinho;
import com.swift.backend.service.CarrinhoService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/carrinho")
public class CarrinhoController {

    private static final Logger log = LoggerFactory.getLogger(CarrinhoController.class);
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
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Carrinho não encontrado para este usuário", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar carrinho do usuário: " + usuarioId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar carrinho",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar carrinho do usuário: " + usuarioId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post("/{usuarioId}")
    public HttpResponse<?> createCarrinho(@PathVariable Integer usuarioId) {
        try {
            Carrinho carrinho = carrinhoService.createCarrinho(usuarioId);
            return HttpResponse.status(HttpStatus.CREATED).body(carrinho);
        } catch (SQLException e) {
            log.error("Erro ao criar carrinho para usuário: " + usuarioId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao criar carrinho",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar carrinho para usuário: " + usuarioId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{carrinhoId}/itens")
    public HttpResponse<?> getItensDoCarrinho(@PathVariable Integer carrinhoId) {
        try {
            List<ItemDoCarrinho> itens = carrinhoService.getItensDoCarrinho(carrinhoId);
            return HttpResponse.ok(itens);
        } catch (SQLException e) {
            log.error("Erro ao buscar itens do carrinho: " + carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar itens do carrinho",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar itens do carrinho: " + carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post("/{carrinhoId}/itens/{itemId}")
    public HttpResponse<?> adicionarItem(@PathVariable Integer carrinhoId, @PathVariable Integer itemId) {
        try {
            carrinhoService.adicionarItemAoCarrinho(carrinhoId, itemId);
            return HttpResponse.ok(new ErrorResponse("Item adicionado ao carrinho", "SUCCESS"));
        } catch (SQLException e) {
            log.error("Erro ao adicionar item {} ao carrinho {}", itemId, carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao adicionar item ao carrinho",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao adicionar item {} ao carrinho {}", itemId, carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Delete("/{carrinhoId}/itens/{itemId}")
    public HttpResponse<?> removerItem(@PathVariable Integer carrinhoId, @PathVariable Integer itemId) {
        try {
            carrinhoService.removerItemDoCarrinho(carrinhoId, itemId);
            return HttpResponse.ok(new ErrorResponse("Item removido do carrinho", "SUCCESS"));
        } catch (SQLException e) {
            log.error("Erro ao remover item {} do carrinho {}", itemId, carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao remover item do carrinho",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao remover item {} do carrinho {}", itemId, carrinhoId, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

