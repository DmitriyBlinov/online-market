package com.dblinov.market.dao;

import com.dblinov.market.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findById(int id);
    User findByName(String name);
    void save(User user);
    boolean update(User user);
    void delete(User user);
    List<User> findAll();
}