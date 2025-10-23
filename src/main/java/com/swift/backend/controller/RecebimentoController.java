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

import com.swift.backend.model.Recebimento;
import com.swift.backend.service.RecebimentoService;

@RestController
@RequestMapping("/api/recebimentos")
@CrossOrigin(origins = "*")
public class RecebimentoController {

    @Autowired
    private RecebimentoService recebimentoService;

    @GetMapping
    public ResponseEntity<List<Recebimento>> getAllRecebimentos() {
        try {
            List<Recebimento> recebimentos = recebimentoService.getAllRecebimentos();
            return ResponseEntity.ok(recebimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recebimento> getRecebimentoById(@PathVariable Integer id) {
        try {
            Optional<Recebimento> recebimento = recebimentoService.getRecebimentoById(id);
            
            if (recebimento.isPresent()) {
                return ResponseEntity.ok(recebimento.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{cdUsuario}")
    public ResponseEntity<List<Recebimento>> getRecebimentosByUsuario(@PathVariable Integer cdUsuario) {
        try {
            List<Recebimento> recebimentos = recebimentoService.getRecebimentosByUsuario(cdUsuario);
            return ResponseEntity.ok(recebimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/categoria/{cdCategoria}")
    public ResponseEntity<List<Recebimento>> getRecebimentosByCategoria(@PathVariable Integer cdCategoria) {
        try {
            List<Recebimento> recebimentos = recebimentoService.getRecebimentosByCategoria(cdCategoria);
            return ResponseEntity.ok(recebimentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Recebimento> createRecebimento(@RequestBody Recebimento recebimento) {
        try {
            Recebimento created = recebimentoService.createRecebimento(recebimento);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecebimento(@PathVariable Integer id, @RequestBody Recebimento recebimento) {
        try {
            recebimentoService.updateRecebimento(id, recebimento);
            return ResponseEntity.ok("Recebimento atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecebimento(@PathVariable Integer id) {
        try {
            boolean deleted = recebimentoService.deleteRecebimento(id);
            
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
