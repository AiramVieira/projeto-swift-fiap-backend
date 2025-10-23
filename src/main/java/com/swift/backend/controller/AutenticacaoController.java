package com.swift.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swift.backend.model.Autenticacao;
import com.swift.backend.service.AutenticacaoService;

@RestController
@RequestMapping("/api/autenticacao")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @GetMapping
    public ResponseEntity<List<Autenticacao>> getAllAutenticacoes() {
        try {
            List<Autenticacao> autenticacoes = autenticacaoService.getAllAutenticacoes();
            return ResponseEntity.ok(autenticacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autenticacao> getAutenticacaoById(@PathVariable Integer id) {
        try {
            Optional<Autenticacao> autenticacao = autenticacaoService.getAutenticacaoById(id);
            
            if (autenticacao.isPresent()) {
                return ResponseEntity.ok(autenticacao.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Autenticacao> createAutenticacao(@RequestBody Autenticacao autenticacao) {
        try {
            Autenticacao created = autenticacaoService.createAutenticacao(autenticacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAutenticacao(@PathVariable Integer id, @RequestBody Autenticacao autenticacao) {
        try {
            autenticacaoService.updateAutenticacao(id, autenticacao);
            return ResponseEntity.ok("Autenticação atualizada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutenticacao(@PathVariable Integer id) {
        try {
            boolean deleted = autenticacaoService.deleteAutenticacao(id);
            
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
