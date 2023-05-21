package com.dblinov.market.dao;

import java.util.List;

public interface Dao<T> {
    Object findById(int id);
    List<T> findAll();
    void save(T o);
    boolean update(T o);
    void delete(T o);
}