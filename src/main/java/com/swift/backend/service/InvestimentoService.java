package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Investimento;
import com.swift.backend.repository.InvestimentoRepository;

@Service
public class InvestimentoService {
    
    @Autowired
    private InvestimentoRepository investimentoRepository;

    public List<Investimento> getAllInvestimentos() {
        return investimentoRepository.findAll();
    }

    public Optional<Investimento> getInvestimentoById(Integer id) {
        return investimentoRepository.findById(id);
    }

    public List<Investimento> getInvestimentosByUsuario(Integer cdUsuario) {
        return investimentoRepository.findByCdUsuario(cdUsuario);
    }

    public List<Investimento> getInvestimentosByTipo(Integer cdTipo) {
        return investimentoRepository.findByCdTipo(cdTipo);
    }

    public Investimento createInvestimento(Investimento investimento) {
        validateInvestimento(investimento);
        return investimentoRepository.save(investimento);
    }

    public void updateInvestimento(Integer id, Investimento investimento) {
        Optional<Investimento> existing = investimentoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Investimento não encontrado com id: " + id);
        }
        
        investimento.setCdInvestimento(id);
        validateInvestimento(investimento);
        investimentoRepository.save(investimento);
    }

    public boolean deleteInvestimento(Integer id) {
        if (investimentoRepository.existsById(id)) {
            investimentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateInvestimento(Investimento investimento) {
        if (investimento.getDtInvestimento() == null) {
            throw new IllegalArgumentException("Data do investimento é obrigatória");
        }
        if (investimento.getVlInvestimento() == null || investimento.getVlInvestimento() <= 0) {
            throw new IllegalArgumentException("Valor do investimento deve ser maior que zero");
        }
        if (investimento.getCdUsuario() == null) {
            throw new IllegalArgumentException("Código do usuário é obrigatório");
        }
        if (investimento.getCdTipo() == null) {
            throw new IllegalArgumentException("Código do tipo de investimento é obrigatório");
        }
    }
}
