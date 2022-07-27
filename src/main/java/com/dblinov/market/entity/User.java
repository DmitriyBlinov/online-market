package com.dblinov.market.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "market")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    /*
    ВМЕСТО STRING ЗДЕСЬ ИСПОЛЬЗОВАЛ БЫ CHAR[], ЧТОБЫ МОЖНО БЫЛО ЗАТЕРЕТЬ
    ПОСЛЕ ИСПОЛЬЗОВАНИЯ, НО ДЛЯ УПРОЩЕНИЯ ВЫБРАН ВАРИАНТ СО STRING
    */
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "is_admin")
    private Boolean isAdmin;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = String.valueOf(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}