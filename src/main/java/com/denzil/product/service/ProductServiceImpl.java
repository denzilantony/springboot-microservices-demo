package com.denzil.product.service;

import com.denzil.product.entity.Product;
import com.denzil.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.debug("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        log.debug("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Product not found with id: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        log.debug("Creating new product: {}", product.getName());
        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException(
                "Product already exists with name: " + product.getName());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        log.debug("Updating product with id: {}", id);
        Product existing = getProductById(id);
        existing.setName(productDetails.getName());
        existing.setDescription(productDetails.getDescription());
        existing.setPrice(productDetails.getPrice());
        existing.setStockQuantity(productDetails.getStockQuantity());
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        log.debug("Deleting product with id: {}", id);
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        log.debug("Searching products by name: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getOutOfStockProducts() {
        log.debug("Fetching out of stock products");
        return productRepository.findOutOfStockProducts();
    }
}