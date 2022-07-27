package com.dblinov.market.entity;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "purchases", schema = "public", catalog = "market")
public class Purchase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "product_id")
    private Integer productId;

    @Basic
    @Column(name = "date")
    private java.sql.Date date;

    //МЕНЯЛ ДЛЯ SUM И QUANTITY НА INTEGER
    @Basic
    @Column(name = "sum")
    private int sum;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    public Purchase() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDateOfPurchase(java.util.Date dateOfPurchase) {
        this.date = new Date(dateOfPurchase.getTime());
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increasePrice(int amount) {
        this.sum += amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id && userId == purchase.userId && productId == purchase.productId && Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, date);
    }
}