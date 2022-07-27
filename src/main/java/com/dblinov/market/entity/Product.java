package com.dblinov.market.entity;

import com.dblinov.market.controller.ProductController;

import javax.persistence.*;

@Entity
@Table(name = "products", schema = "public", catalog = "market")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private int price;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "version")
    private Long version;

    public Product() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    //TODO исправить и перенести
    public void increaseQuantity() {
        quantity++;
    }

    //TODO исправить и перенести
    public void decreaseQuantity(int amount) {
        if (!(quantity - amount < 0)) {
            quantity -= amount;
        }
    }
}