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

import com.swift.backend.model.Investimento;
import com.swift.backend.service.InvestimentoService;

@RestController
@RequestMapping("/api/investimentos")
@CrossOrigin(origins = "*")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @GetMapping
    public ResponseEntity<List<Investimento>> getAllInvestimentos() {
        try {
            List<Investimento> investimentos = investimentoService.getAllInvestimentos();
            return ResponseEntity.ok(investimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> getInvestimentoById(@PathVariable Integer id) {
        try {
            Optional<Investimento> investimento = investimentoService.getInvestimentoById(id);
            
            if (investimento.isPresent()) {
                return ResponseEntity.ok(investimento.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{cdUsuario}")
    public ResponseEntity<List<Investimento>> getInvestimentosByUsuario(@PathVariable Integer cdUsuario) {
        try {
            List<Investimento> investimentos = investimentoService.getInvestimentosByUsuario(cdUsuario);
            return ResponseEntity.ok(investimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/tipo/{cdTipo}")
    public ResponseEntity<List<Investimento>> getInvestimentosByTipo(@PathVariable Integer cdTipo) {
        try {
            List<Investimento> investimentos = investimentoService.getInvestimentosByTipo(cdTipo);
            return ResponseEntity.ok(investimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Investimento> createInvestimento(@RequestBody Investimento investimento) {
        try {
            Investimento created = investimentoService.createInvestimento(investimento);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInvestimento(@PathVariable Integer id, @RequestBody Investimento investimento) {
        try {
            investimentoService.updateInvestimento(id, investimento);
            return ResponseEntity.ok("Investimento atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestimento(@PathVariable Integer id) {
        try {
            boolean deleted = investimentoService.deleteInvestimento(id);
            
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
