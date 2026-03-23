package com.denzil.product.service;

import com.denzil.product.entity.Product;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> searchProductsByName(String name);

    List<Product> getOutOfStockProducts();
}