package com.dblinov.market.dao;

import com.dblinov.market.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends Dao<Product> {
    Optional<Product> findById(int id);
    void save(Product product);
    boolean update(Product product);
    void delete(Product product);
    List<Product> findAll();
}