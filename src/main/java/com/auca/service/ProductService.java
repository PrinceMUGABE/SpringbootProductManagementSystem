package com.auca.service;

import com.auca.modal.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Product findProductById(Long productId);
    List<Product> getAllProducts();
    void deleteProduct(Long productId);

    List<Product> searchProductsByName(String name);
}
