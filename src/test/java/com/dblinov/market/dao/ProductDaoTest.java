package com.dblinov.market.dao;

import com.dblinov.market.dao.impl.ProductDaoImpl;
import com.dblinov.market.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private static Product firstProduct;
    private static Product secondProduct;
    private static Product thirdProduct;
    private final static ProductDao productDao = new ProductDaoImpl();
    private static final Logger logger = LogManager.getLogger(ProductDaoTest.class);

    @BeforeAll
    static void setup() {
        firstProduct = new Product("Gibson Les Paul", 100000, 5, "The guitar was designed by factory manager John Huis and his team with input from and endorsement by guitarist Les Paul");
        secondProduct = new Product("Fender Stratocaster", 90000, 5, "The Fender Musical Instruments Corporation has continuously manufactured the Stratocaster since 1954");
        thirdProduct = new Product("B.C. Rich Mockingbird", 70000, 5, "A solid body electric guitar manufactured by B.C. Rich in 1976");
        productDao.save(firstProduct);
        productDao.save(secondProduct);
        logger.info("Executed @BeforeAll");
    }

    @Test
    void findById() {
        final int id = firstProduct.getId();
        Product product = productDao.findById(id).get();
        assertEquals(id, product.getId());
    }

    @Test
    void save() {
        productDao.save(thirdProduct);
        assertNotNull(productDao.findById(thirdProduct.getId()).get());
    }

    @Test
    void update() {
        final int quantity = secondProduct.getQuantity();
        secondProduct.setQuantity(quantity - 1);
        productDao.save(secondProduct);
        assertEquals(secondProduct.getQuantity(), quantity - 1);
    }

    @Test
    void delete() {
        productDao.delete(secondProduct);
        assertFalse(productDao.findAll().contains(secondProduct));
    }

    @Test
    void findAll() {
        List<Product> products = productDao.findAll();
        assertFalse(products.isEmpty());
    }
}