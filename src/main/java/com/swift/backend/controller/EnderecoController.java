package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import com.swift.backend.model.Endereco;
import com.swift.backend.service.EnderecoService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Inject
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @Get
    public HttpResponse<List<Endereco>> getAllEnderecos() {
        try {
            List<Endereco> enderecos = enderecoService.getAllEnderecos();
            return HttpResponse.ok(enderecos);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getEnderecoById(@PathVariable Integer id) {
        try {
            Optional<Endereco> endereco = enderecoService.getEnderecoById(id);
            
            if (endereco.isPresent()) {
                return HttpResponse.ok(endereco.get());
            } else {
                return HttpResponse.notFound("Endereço não encontrado");
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<?> createEndereco(@Body Endereco endereco) {
        try {
            Endereco created = enderecoService.createEndereco(endereco);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateEndereco(@PathVariable Integer id, @Body Endereco endereco) {
        try {
            enderecoService.updateEndereco(id, endereco);
            return HttpResponse.ok("Endereço atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}

