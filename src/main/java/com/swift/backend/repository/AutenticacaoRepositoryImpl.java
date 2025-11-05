package com.swift.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.swift.backend.model.Autenticacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class AutenticacaoRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Optional<Autenticacao> findByEmailAndSenhaAndStatusConta(String email, String senha) {
        String jpql = "SELECT DISTINCT a FROM Autenticacao a LEFT JOIN FETCH a.usuario WHERE a.email = :email AND a.senha = :senha AND a.statusConta = 'ATIVO' ORDER BY a.cdAutenticacao desc";
        TypedQuery<Autenticacao> query = entityManager.createQuery(jpql, Autenticacao.class);
        query.setParameter("email", email);
        query.setParameter("senha", senha);
        query.setMaxResults(1);
        
        try {
            Autenticacao result = query.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

