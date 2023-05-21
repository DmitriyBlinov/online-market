package com.dblinov.market.dao;

import com.dblinov.market.entity.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao extends Dao<Purchase> {
    Optional<Purchase> findById(int id);
    void save(Purchase purchase);
    boolean update(Purchase purchase);
    void delete(Purchase purchase);
    List<Purchase> findAll();
}
