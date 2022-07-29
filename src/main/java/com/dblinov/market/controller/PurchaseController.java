package com.dblinov.market.controller;

import com.dblinov.market.dao.PurchaseDao;
import com.dblinov.market.entity.Purchase;

import java.io.Serializable;
import java.util.List;

public class PurchaseController implements Serializable {
    private static final long serialVersionUID = -1769512858853931581L;
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

    public List<Purchase> findAllPurchases() {
        return purchaseDao.findAll();
    }
}
