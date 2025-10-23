package com.swift.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swift.backend.model.Gasto;
import com.swift.backend.repository.GastoRepository;

@Service
public class GastoService {
    
    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> getAllGastos() {
        return gastoRepository.findAll();
    }

    public Optional<Gasto> getGastoById(Integer id) {
        return gastoRepository.findById(id);
    }

    public List<Gasto> getGastosByUsuario(Integer cdUsuario) {
        return gastoRepository.findByCdUsuario(cdUsuario);
    }

    public List<Gasto> getGastosByCategoria(Integer cdCategoria) {
        return gastoRepository.findByCdCategoria(cdCategoria);
    }

    public Gasto createGasto(Gasto gasto) {
        validateGasto(gasto);
        return gastoRepository.save(gasto);
    }

    public void updateGasto(Integer id, Gasto gasto) {
        Optional<Gasto> existing = gastoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Gasto não encontrado com id: " + id);
        }
        
        gasto.setCdGasto(id);
        validateGasto(gasto);
        gastoRepository.save(gasto);
    }

    public boolean deleteGasto(Integer id) {
        if (gastoRepository.existsById(id)) {
            gastoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateGasto(Gasto gasto) {
        if (gasto.getNmGasto() == null || gasto.getNmGasto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do gasto é obrigatório");
        }
        if (gasto.getDtGasto() == null) {
            throw new IllegalArgumentException("Data do gasto é obrigatória");
        }
        if (gasto.getVlGasto() == null || gasto.getVlGasto() <= 0) {
            throw new IllegalArgumentException("Valor do gasto deve ser maior que zero");
        }
        if (gasto.getCdUsuario() == null) {
            throw new IllegalArgumentException("Código do usuário é obrigatório");
        }
        if (gasto.getCdCategoria() == null) {
            throw new IllegalArgumentException("Código da categoria é obrigatório");
        }
    }
}
