package com.dblinov.market.controller;

import com.dblinov.market.dao.PurchaseDao;
import com.dblinov.market.entity.Purchase;

import java.util.List;

public class PurchaseController {
    private PurchaseDao purchaseDao = new PurchaseDao();

    public PurchaseController() {
    }

    public Purchase findById(int id) {
        return purchaseDao.findById(id);
    }

    public void savePurchase(Purchase purchase) {
        purchaseDao.save(purchase);
    }

    public void deletePurchase(Purchase purchase) {
        purchaseDao.delete(purchase);
    }

    public void updatePurchase(Purchase purchase) {
        purchaseDao.update(purchase);
    }

    public List<Purchase> findAllProducts() {
        return purchaseDao.findAll();
    }
}
