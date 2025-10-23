package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Recebimento;
import com.swift.backend.repository.RecebimentoRepository;

@Service
public class RecebimentoService {
    
    @Autowired
    private RecebimentoRepository recebimentoRepository;

    public List<Recebimento> getAllRecebimentos() {
        return recebimentoRepository.findAll();
    }

    public Optional<Recebimento> getRecebimentoById(Integer id) {
        return recebimentoRepository.findById(id);
    }

    public List<Recebimento> getRecebimentosByUsuario(Integer cdUsuario) {
        return recebimentoRepository.findByCdUsuario(cdUsuario);
    }

    public List<Recebimento> getRecebimentosByCategoria(Integer cdCategoria) {
        return recebimentoRepository.findByCdCategoria(cdCategoria);
    }

    public Recebimento createRecebimento(Recebimento recebimento) {
        validateRecebimento(recebimento);
        return recebimentoRepository.save(recebimento);
    }

    public void updateRecebimento(Integer id, Recebimento recebimento) {
        Optional<Recebimento> existing = recebimentoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Recebimento não encontrado com id: " + id);
        }
        
        recebimento.setCdRecebimento(id);
        validateRecebimento(recebimento);
        recebimentoRepository.save(recebimento);
    }

    public boolean deleteRecebimento(Integer id) {
        if (recebimentoRepository.existsById(id)) {
            recebimentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateRecebimento(Recebimento recebimento) {
        if (recebimento.getNmRecebimento() == null || recebimento.getNmRecebimento().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do recebimento é obrigatório");
        }
        if (recebimento.getDtRecebimento() == null) {
            throw new IllegalArgumentException("Data do recebimento é obrigatória");
        }
        if (recebimento.getVlRecebimento() == null || recebimento.getVlRecebimento() <= 0) {
            throw new IllegalArgumentException("Valor do recebimento deve ser maior que zero");
        }
        if (recebimento.getCdUsuario() == null) {
            throw new IllegalArgumentException("Código do usuário é obrigatório");
        }
        if (recebimento.getCdCategoria() == null) {
            throw new IllegalArgumentException("Código da categoria é obrigatório");
        }
    }
}
