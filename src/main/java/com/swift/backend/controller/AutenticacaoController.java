package com.swift.backend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/buscar")
    public ResponseEntity<Integer> getAutenticacaoByEmailAndSenha(
            @RequestParam String email, 
            @RequestParam String senha) {
        try {
            Integer autenticacaoAtiva = autenticacaoService.getCdUsuarioByEmailAndSenha(email, senha);
            System.out.println("AutenticacaoAtiva: " + autenticacaoAtiva);
            if (autenticacaoAtiva != null) {
                return ResponseEntity.ok(autenticacaoAtiva);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Autenticacao> createAutenticacao(@RequestBody Autenticacao autenticacao) {
        try {
            autenticacao.setStatusConta("ATIVO");
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
