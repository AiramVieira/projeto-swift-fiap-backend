package com.swift.backend.dao;

import com.swift.backend.model.Categoria;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoriaDAO - Testes de Integração com Banco")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
class CategoriaDAOTest {

    private CategoriaDAO categoriaDAO;
    private static Integer categoriaIdCriada;

    @BeforeEach
    void setUp() {
        categoriaDAO = new CategoriaDAO();
    }

    @Test
    @Order(1)
    @DisplayName("Deve buscar todas as categorias")
    void testFindAll() throws SQLException {
        List<Categoria> categorias = categoriaDAO.findAll();
        
        assertNotNull(categorias);
        assertTrue(categorias.size() >= 0);
    }

    @Test
    @Order(2)
    @DisplayName("Deve criar uma nova categoria")
    void testSave() throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Categoria Teste");

        Categoria categoriaSalva = categoriaDAO.save(categoria);

        assertNotNull(categoriaSalva);
        assertNotNull(categoriaSalva.getId());
        assertEquals("Categoria Teste", categoriaSalva.getDescricao());
        
        categoriaIdCriada = categoriaSalva.getId();
    }

    @Test
    @Order(3)
    @DisplayName("Deve buscar categoria por ID")
    void testFindById() throws SQLException {
        if (categoriaIdCriada == null) {
            testSave();
        }

        Optional<Categoria> categoria = categoriaDAO.findById(categoriaIdCriada);

        assertTrue(categoria.isPresent());
        assertEquals(categoriaIdCriada, categoria.get().getId());
        assertEquals("Categoria Teste", categoria.get().getDescricao());
    }

    @Test
    @Order(4)
    @DisplayName("Deve atualizar uma categoria existente")
    void testUpdate() throws SQLException {
        if (categoriaIdCriada == null) {
            testSave();
        }

        Categoria categoria = new Categoria();
        categoria.setId(categoriaIdCriada);
        categoria.setDescricao("Categoria Atualizada");

        assertDoesNotThrow(() -> categoriaDAO.update(categoria));

        Optional<Categoria> categoriaAtualizada = categoriaDAO.findById(categoriaIdCriada);
        assertTrue(categoriaAtualizada.isPresent());
        assertEquals("Categoria Atualizada", categoriaAtualizada.get().getDescricao());
    }

    @Test
    @Order(5)
    @DisplayName("Deve deletar uma categoria")
    void testDelete() throws SQLException {
        if (categoriaIdCriada == null) {
            testSave();
        }

        boolean deletado = categoriaDAO.delete(categoriaIdCriada);
        assertTrue(deletado);

        Optional<Categoria> categoria = categoriaDAO.findById(categoriaIdCriada);
        assertFalse(categoria.isPresent());
    }

    @Test
    @Order(6)
    @DisplayName("Deve retornar false ao deletar ID inexistente")
    void testDeleteNotFound() throws SQLException {
        boolean deletado = categoriaDAO.delete(999999);
        assertFalse(deletado);
    }

    @Test
    @Order(7)
    @DisplayName("Deve retornar vazio ao buscar ID inexistente")
    void testFindByIdNotFound() throws SQLException {
        Optional<Categoria> categoria = categoriaDAO.findById(999999);
        assertFalse(categoria.isPresent());
    }
}

