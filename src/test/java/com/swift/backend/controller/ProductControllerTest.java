package com.swift.backend.controller;

import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.Product;
import com.swift.backend.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("ProductController - Testes Unitários")
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product productMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        productMock = new Product();
        productMock.setId(1);
        productMock.setNome("Notebook Dell");
        productMock.setCategoriaId(1);
        productMock.setPreco(3500.00f);
        productMock.setEstoque(10);
        productMock.setDescricao("Notebook com i7");
    }

    @Test
    @DisplayName("GET /api/products - Deve retornar todos os produtos com sucesso")
    void testGetAllProductsSuccess() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(productMock);
        
        when(productService.getAllProducts()).thenReturn(products);

        HttpResponse<?> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        List<Product> responseProducts = (List<Product>) response.body();
        assertEquals(1, responseProducts.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/products - Deve retornar lista vazia quando não há produtos")
    void testGetAllProductsEmptyList() throws SQLException {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());

        HttpResponse<?> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatus());
        List<Product> responseProducts = (List<Product>) response.body();
        assertTrue(responseProducts.isEmpty());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/products - Deve retornar erro 500 quando ocorrer exceção")
    void testGetAllProductsError() throws SQLException {
        when(productService.getAllProducts()).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.getAllProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/products/{id} - Deve retornar produto por ID com sucesso")
    void testGetProductByIdSuccess() throws SQLException {
        when(productService.getProductById(1)).thenReturn(Optional.of(productMock));

        HttpResponse<?> response = productController.getProductById(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        Product product = (Product) response.body();
        assertEquals(1, product.getId());
        assertEquals("Notebook Dell", product.getNome());
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    @DisplayName("GET /api/products/{id} - Deve retornar 404 quando produto não encontrado")
    void testGetProductByIdNotFound() throws SQLException {
        when(productService.getProductById(999)).thenReturn(Optional.empty());

        HttpResponse<?> response = productController.getProductById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(productService, times(1)).getProductById(999);
    }

    @Test
    @DisplayName("GET /api/products/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetProductByIdError() throws SQLException {
        when(productService.getProductById(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.getProductById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    @DisplayName("GET /api/products/categoria/{categoriaId} - Deve retornar produtos por categoria")
    void testGetProductsByCategoriaSuccess() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(productMock);
        
        when(productService.getProductsByCategoria(1)).thenReturn(products);

        HttpResponse<?> response = productController.getProductsByCategoria(1);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        verify(productService, times(1)).getProductsByCategoria(1);
    }

    @Test
    @DisplayName("GET /api/products/categoria/{categoriaId} - Deve retornar erro 500 quando ocorrer exceção")
    void testGetProductsByCategoriaError() throws SQLException {
        when(productService.getProductsByCategoria(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.getProductsByCategoria(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).getProductsByCategoria(1);
    }

    @Test
    @DisplayName("GET /api/products/search - Deve buscar produtos por nome")
    void testSearchProductsSuccess() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(productMock);
        
        when(productService.searchProducts("Notebook")).thenReturn(products);

        HttpResponse<?> response = productController.searchProducts("Notebook");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        verify(productService, times(1)).searchProducts("Notebook");
    }

    @Test
    @DisplayName("GET /api/products/search - Deve retornar erro 400 quando nome está vazio")
    void testSearchProductsEmptyName() throws SQLException {
        HttpResponse<?> response = productController.searchProducts("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(productService, never()).searchProducts(anyString());
    }

    @Test
    @DisplayName("GET /api/products/search - Deve retornar erro 400 quando nome é null")
    void testSearchProductsNullName() throws SQLException {
        HttpResponse<?> response = productController.searchProducts(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(productService, never()).searchProducts(anyString());
    }

    @Test
    @DisplayName("GET /api/products/search - Deve retornar erro 500 quando ocorrer exceção")
    void testSearchProductsError() throws SQLException {
        when(productService.searchProducts(anyString())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.searchProducts("Notebook");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).searchProducts("Notebook");
    }

    @Test
    @DisplayName("POST /api/products - Deve criar produto com sucesso")
    void testCreateProductSuccess() throws SQLException {
        Product novoProduto = new Product();
        novoProduto.setNome("Mouse Gamer");
        novoProduto.setCategoriaId(1);
        novoProduto.setPreco(150.00f);
        novoProduto.setEstoque(50);

        Product produtoSalvo = new Product();
        produtoSalvo.setId(2);
        produtoSalvo.setNome(novoProduto.getNome());
        produtoSalvo.setCategoriaId(novoProduto.getCategoriaId());
        produtoSalvo.setPreco(novoProduto.getPreco());
        produtoSalvo.setEstoque(novoProduto.getEstoque());

        when(productService.createProduct(any(Product.class))).thenReturn(produtoSalvo);

        HttpResponse<?> response = productController.createProduct(novoProduto);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        Product product = (Product) response.body();
        assertEquals(2, product.getId());
        assertEquals("Mouse Gamer", product.getNome());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("POST /api/products - Deve retornar erro 400 com dados inválidos")
    void testCreateProductInvalidData() throws SQLException {
        Product produtoInvalido = new Product();
        produtoInvalido.setNome("");

        when(productService.createProduct(any(Product.class)))
                .thenThrow(new IllegalArgumentException("Nome do produto é obrigatório"));

        HttpResponse<?> response = productController.createProduct(produtoInvalido);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("POST /api/products - Deve retornar erro 500 quando ocorrer exceção")
    void testCreateProductError() throws SQLException {
        when(productService.createProduct(any(Product.class)))
                .thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.createProduct(productMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("PUT /api/products/{id} - Deve atualizar produto com sucesso")
    void testUpdateProductSuccess() throws SQLException {
        Product produtoAtualizado = new Product();
        produtoAtualizado.setNome("Notebook Dell Atualizado");
        produtoAtualizado.setPreco(4000.00f);

        doNothing().when(productService).updateProduct(eq(1), any(Product.class));

        HttpResponse<?> response = productController.updateProduct(1, produtoAtualizado);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        ErrorResponse errorResponse = (ErrorResponse) response.body();
        assertEquals("Produto atualizado com sucesso", errorResponse.getMessage());
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    @DisplayName("PUT /api/products/{id} - Deve retornar erro 400 quando produto não encontrado")
    void testUpdateProductNotFound() throws SQLException {
        Product produtoAtualizado = new Product();
        produtoAtualizado.setNome("Teste");

        doThrow(new IllegalArgumentException("Produto não encontrado com id: 999"))
                .when(productService).updateProduct(eq(999), any(Product.class));

        HttpResponse<?> response = productController.updateProduct(999, produtoAtualizado);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        verify(productService, times(1)).updateProduct(eq(999), any(Product.class));
    }

    @Test
    @DisplayName("PUT /api/products/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testUpdateProductError() throws SQLException {
        doThrow(new SQLException("Database error"))
                .when(productService).updateProduct(anyInt(), any(Product.class));

        HttpResponse<?> response = productController.updateProduct(1, productMock);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - Deve deletar produto com sucesso")
    void testDeleteProductSuccess() throws SQLException {
        when(productService.deleteProduct(1)).thenReturn(true);

        HttpResponse<?> response = productController.deleteProduct(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        verify(productService, times(1)).deleteProduct(1);
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - Deve retornar 404 quando produto não encontrado")
    void testDeleteProductNotFound() throws SQLException {
        when(productService.deleteProduct(999)).thenReturn(false);

        HttpResponse<?> response = productController.deleteProduct(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        verify(productService, times(1)).deleteProduct(999);
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - Deve retornar erro 500 quando ocorrer exceção")
    void testDeleteProductError() throws SQLException {
        when(productService.deleteProduct(anyInt())).thenThrow(new SQLException("Database error"));

        HttpResponse<?> response = productController.deleteProduct(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
        verify(productService, times(1)).deleteProduct(1);
    }
}

