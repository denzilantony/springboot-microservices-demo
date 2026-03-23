package com.denzil.product;

import com.denzil.product.controller.ProductController;
import com.denzil.product.entity.Product;
import com.denzil.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Tests")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("29.99"))
                .stockQuantity(100)
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/products - should return all products")
    void shouldReturnAllProducts() throws Exception {
        when(productService.getAllProducts())
                .thenReturn(Arrays.asList(testProduct));

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Test Product"))
                .andExpect(jsonPath("$[0].price")
                        .value(29.99));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - should return product by id")
    void shouldReturnProductById() throws Exception {
        when(productService.getProductById(1L))
                .thenReturn(testProduct);

        mockMvc.perform(get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(29.99));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    @DisplayName("POST /api/v1/products - should create product")
    void shouldCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class)))
                .thenReturn(testProduct);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(29.99));

        verify(productService, times(1))
                .createProduct(any(Product.class));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - should update product")
    void shouldUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(Product.class)))
                .thenReturn(testProduct);

        mockMvc.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1))
                .updateProduct(eq(1L), any(Product.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - should delete product")
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}