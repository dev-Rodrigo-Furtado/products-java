package com.example.demo.repository;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductRepositoryInterface {

    void save(Product product);
    List<Product> findAllProducts();
}
