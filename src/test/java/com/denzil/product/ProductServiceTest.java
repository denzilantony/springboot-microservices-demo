package com.denzil.product;

import com.denzil.product.entity.Product;
import com.denzil.product.repository.ProductRepository;
import com.denzil.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
    @DisplayName("Should return all products successfully")
    void shouldReturnAllProducts() {
        when(productRepository.findAll())
                .thenReturn(Arrays.asList(testProduct));

        List<Product> products = productService.getAllProducts();

        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return product by id successfully")
    void shouldReturnProductById() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(testProduct));

        Product found = productService.getProductById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getName()).isEqualTo("Test Product");
        assertThat(found.getPrice()).isEqualByComparingTo("29.99");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when product not found")
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found with id: 99");
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() {
        when(productRepository.existsByName("Test Product"))
                .thenReturn(false);
        when(productRepository.save(any(Product.class)))
                .thenReturn(testProduct);

        Product created = productService.createProduct(testProduct);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Test Product");
        assertThat(created.getPrice()).isEqualByComparingTo("29.99");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when creating duplicate product")
    void shouldThrowExceptionForDuplicateProduct() {
        when(productRepository.existsByName("Test Product"))
                .thenReturn(true);

        assertThatThrownBy(() -> productService.createProduct(testProduct))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product already exists with name");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update product successfully")
    void shouldUpdateProductSuccessfully() {
        Product updatedDetails = Product.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("49.99"))
                .stockQuantity(50)
                .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class)))
                .thenReturn(updatedDetails);

        Product updated = productService.updateProduct(1L, updatedDetails);

        assertThat(updated.getName()).isEqualTo("Updated Product");
        assertThat(updated.getPrice()).isEqualByComparingTo("49.99");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProductSuccessfully() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(any(Product.class));
    }
}