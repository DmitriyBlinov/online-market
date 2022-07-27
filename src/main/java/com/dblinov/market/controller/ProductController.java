package com.dblinov.market.controller;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.entity.Product;

import java.util.List;

public class ProductController {
    private ProductDao productDao = new ProductDao();

    public ProductController() {
    }

    public Product findProduct(int id) {
        return productDao.findById(id);
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }

    public void deleteProduct(Product product) {
        productDao.delete(product);
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    /*public Purchase findPurchaseById(int id) {
        return productDao.findPurchaseById(id);
    }*/
}