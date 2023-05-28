package com.dblinov.market.dao;

import com.dblinov.market.dao.impl.UserDaoImpl;
import com.dblinov.market.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private static User firstUser;
    private static User secondUser;
    private final static UserDao userDao = new UserDaoImpl();
    private static final Logger logger = LogManager.getLogger(UserDaoTest.class);

    @BeforeAll
    static void setup() {
        firstUser = new User("Thomas Anderson", "geof", false);
        secondUser = new User("Michael Scott", "twss", false);
        userDao.save(firstUser);
        logger.info("Executed @BeforeAll");
    }

    @Test
    void findById() {
        final int id = firstUser.getId();
        User user = userDao.findById(id).get();
        assertEquals(id, user.getId());
    }

    @Test
    void findByName() {
        final String name = firstUser.getName();
        User user = userDao.findByName(name);
        assertEquals(firstUser.getName(), user.getName());
    }

    @Test
    void save() {
        userDao.save(secondUser);
        assertNotNull(userDao.findById(secondUser.getId()).get());
    }

    @Test
    void update() {
        secondUser.setName("Michael Scarn");
        userDao.save(secondUser);
        assertEquals(secondUser.getName(), userDao.findById(secondUser.getId()).get().getName());
    }

    @Test
    void delete() {
        userDao.delete(secondUser);
        assertFalse(userDao.findAll().contains(secondUser));
    }

    @Test
    void findAll() {
        List<User> users = userDao.findAll();
        assertFalse(users.isEmpty());
    }
}