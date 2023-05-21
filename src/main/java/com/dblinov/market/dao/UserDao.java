package com.dblinov.market.dao;

import com.dblinov.market.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(int id);
    User findByName(String name);
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getAllUsers();
}