package com.dblinov.market.dao.impl;

import com.dblinov.market.dao.UserDao;
import com.dblinov.market.entity.Purchase;
import com.dblinov.market.utils.HibernateSessionFactory;
import com.dblinov.market.entity.User;
import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao, Serializable {
    private static final long serialVersionUID = -1769511858853931581L;
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    public Optional<User> findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.of(session.get(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public User findByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String query = String.format("SELECT * FROM users WHERE name = '%s'", name);
        User user = null;
        try {
            List<User> users = session.createNativeQuery(query, User.class).list();
            if (!users.isEmpty()) {
                user = users.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public void save(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            logger.info("The User ID {} was saved", user.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public void update(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
            logger.info("The User ID {} was updated", user.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public void delete(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
            logger.info("The User ID {} was deleted", user.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<User> getAllUsers() {
        final String query = "SELECT * FROM users";
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createNativeQuery(query, User.class).list();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }
}