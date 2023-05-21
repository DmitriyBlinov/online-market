package com.dblinov.market.dao;

import com.dblinov.market.entity.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao {
    Optional<Purchase> findById(int id);
    void save(Purchase purchase);
    void update(Purchase purchase);
    void delete(Purchase purchase);
    List<Purchase> getAllPurchases();
}
