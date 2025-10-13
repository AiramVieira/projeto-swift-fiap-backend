package com.swift.backend.dao;

import com.swift.backend.model.Endereco;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnderecoDAO - Testes de Integração com Banco")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "DB_TEST_ENABLED", matches = "true")
class EnderecoDAOTest {

    private EnderecoDAO enderecoDAO;
    private static Integer enderecoIdCriado;

    @BeforeEach
    void setUp() {
        enderecoDAO = new EnderecoDAO();
    }

    @Test
    @Order(1)
    @DisplayName("Deve buscar todos os endereços")
    void testFindAll() throws SQLException {
        List<Endereco> enderecos = enderecoDAO.findAll();
        
        assertNotNull(enderecos);
        assertTrue(enderecos.size() >= 0);
    }

    @Test
    @Order(2)
    @DisplayName("Deve criar um novo endereço")
    void testSave() throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setDescricao("Rua Teste Unitário, 123");
        endereco.setCep("99999-999");
        endereco.setLatitude(new BigDecimal("-23.550520"));
        endereco.setLongitude(new BigDecimal("-46.633308"));

        Endereco enderecoSalvo = enderecoDAO.save(endereco);

        assertNotNull(enderecoSalvo);
        assertNotNull(enderecoSalvo.getId());
        assertEquals("Rua Teste Unitário, 123", enderecoSalvo.getDescricao());
        assertEquals("99999-999", enderecoSalvo.getCep());
        
        enderecoIdCriado = enderecoSalvo.getId();
    }

    @Test
    @Order(3)
    @DisplayName("Deve buscar endereço por ID")
    void testFindById() throws SQLException {
        if (enderecoIdCriado == null) {
            testSave();
        }

        Optional<Endereco> endereco = enderecoDAO.findById(enderecoIdCriado);

        assertTrue(endereco.isPresent());
        assertEquals(enderecoIdCriado, endereco.get().getId());
        assertEquals("Rua Teste Unitário, 123", endereco.get().getDescricao());
    }

    @Test
    @Order(4)
    @DisplayName("Deve atualizar um endereço existente")
    void testUpdate() throws SQLException {
        if (enderecoIdCriado == null) {
            testSave();
        }

        Endereco endereco = new Endereco();
        endereco.setId(enderecoIdCriado);
        endereco.setDescricao("Rua Teste Atualizado, 456");
        endereco.setCep("88888-888");
        endereco.setLatitude(new BigDecimal("-23.560000"));
        endereco.setLongitude(new BigDecimal("-46.640000"));

        assertDoesNotThrow(() -> enderecoDAO.update(endereco));

        Optional<Endereco> enderecoAtualizado = enderecoDAO.findById(enderecoIdCriado);
        assertTrue(enderecoAtualizado.isPresent());
        assertEquals("Rua Teste Atualizado, 456", enderecoAtualizado.get().getDescricao());
        assertEquals("88888-888", enderecoAtualizado.get().getCep());
    }

    @Test
    @Order(5)
    @DisplayName("Deve retornar vazio ao buscar ID inexistente")
    void testFindByIdNotFound() throws SQLException {
        Optional<Endereco> endereco = enderecoDAO.findById(999999);
        
        assertFalse(endereco.isPresent());
    }

    @Test
    @Order(6)
    @DisplayName("Deve criar endereço sem coordenadas")
    void testSaveWithoutCoordinates() throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setDescricao("Rua Sem Coordenadas");
        endereco.setCep("77777-777");

        Endereco enderecoSalvo = enderecoDAO.save(endereco);

        assertNotNull(enderecoSalvo);
        assertNotNull(enderecoSalvo.getId());
        assertNull(enderecoSalvo.getLatitude());
        assertNull(enderecoSalvo.getLongitude());
    }

    @Test
    @Order(7)
    @DisplayName("Deve verificar que lista de endereços não está vazia após inserções")
    void testFindAllNotEmpty() throws SQLException {
        List<Endereco> enderecos = enderecoDAO.findAll();
        
        assertNotNull(enderecos);
        assertFalse(enderecos.isEmpty());
    }

    @Test
    @Order(8)
    @DisplayName("Deve lidar com BigDecimal em coordenadas")
    void testBigDecimalCoordinates() throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setDescricao("Teste BigDecimal");
        endereco.setCep("66666-666");
        endereco.setLatitude(new BigDecimal("-23.123456789"));
        endereco.setLongitude(new BigDecimal("-46.987654321"));

        Endereco enderecoSalvo = enderecoDAO.save(endereco);

        assertNotNull(enderecoSalvo.getLatitude());
        assertNotNull(enderecoSalvo.getLongitude());
        assertTrue(enderecoSalvo.getLatitude().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(enderecoSalvo.getLongitude().compareTo(BigDecimal.ZERO) < 0);
    }
}

