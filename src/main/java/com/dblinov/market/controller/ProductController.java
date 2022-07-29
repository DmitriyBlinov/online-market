package com.dblinov.market.controller;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.entity.Product;

import java.io.Serializable;
import java.util.List;

public class ProductController implements Serializable {
    private static final long serialVersionUID = -1769512858853931284L;
    private Product product;
    private ProductDao productDao = new ProductDao();

    public ProductController() {
    }

    public ProductController(Product product) {
        this.product = product;
    }

    public Product findById(int id) {
        return productDao.findById(id);
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }

    public void deleteProduct(Product product) {
        productDao.delete(product);
    }

    public boolean updateProduct(Product product) {
        return productDao.update(product);
    }

    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    public synchronized void decreaseQuantity(int amount) {
        if (!(product.getQuantity() - amount < 0)) {
            int quantity = product.getQuantity() - amount;
            product.setQuantity(quantity);
        }
    }
}