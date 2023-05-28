package com.dblinov.market.dao;

import com.dblinov.market.dao.impl.ProductDaoImpl;
import com.dblinov.market.dao.impl.PurchaseDaoImpl;
import com.dblinov.market.dao.impl.UserDaoImpl;
import com.dblinov.market.entity.Product;
import com.dblinov.market.entity.Purchase;
import com.dblinov.market.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseDaoTest {
    private static Purchase firstPurchase;
    private static Purchase secondPurchase;
    private final static PurchaseDao purchaseDao = new PurchaseDaoImpl();
    private final static UserDao userDao = new UserDaoImpl();
    private final static ProductDao productDao = new ProductDaoImpl();
    private static final Logger logger = LogManager.getLogger(PurchaseDaoTest.class);

    @BeforeAll
    static void setup() {
        Product product = new Product("Gibson Les Paul", 100000, 5, "The guitar was designed by factory manager John Huis and his team with input from and endorsement by guitarist Les Paul");
        User user = new User("Thomas Anderson", "geof", false);
        productDao.save(product);
        userDao.save(user);
        firstPurchase = new Purchase(user.getId(), product.getId(), new Date(System.currentTimeMillis()), 90000, 1);
        secondPurchase = new Purchase(user.getId(), product.getId(), new Date(System.currentTimeMillis()), 90000, 1);
        purchaseDao.save(firstPurchase);
        logger.info("Executed @BeforeAll");
    }

    @Test
    void findById() {
        final int id = firstPurchase.getId();
        Purchase purchase = purchaseDao.findById(id).get();
        assertEquals(id, purchase.getId());
    }

    @Test
    void save() {
        purchaseDao.save(secondPurchase);
        assertNotNull(purchaseDao.findById(secondPurchase.getId()).get());
    }

    @Test
    void update() {
        final int quantity = secondPurchase.getQuantity();
        secondPurchase.setQuantity(quantity - 1);
        purchaseDao.save(secondPurchase);
        assertEquals(secondPurchase.getQuantity(), purchaseDao.findById(secondPurchase.getId()).get().getQuantity());
    }

    @Test
    void delete() {
        purchaseDao.delete(secondPurchase);
        assertFalse(purchaseDao.findAll().contains(secondPurchase));
    }

    @Test
    void findAll() {
        List<Purchase> purchases = purchaseDao.findAll();
        assertFalse(purchases.isEmpty());
    }
}