package com.dblinov.market.controller;

import com.dblinov.market.dao.UserDao;
import com.dblinov.market.entity.Purchase;
import com.dblinov.market.entity.User;

import java.util.List;

public class UserController {
    private UserDao usersDao = new UserDao();

    public UserController() {
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public User findUserByName(String name) {
        return usersDao.findByName(name);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }


    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public Purchase findPurchaseById(int id) {
        return usersDao.findPurchaseById(id);
    }
}