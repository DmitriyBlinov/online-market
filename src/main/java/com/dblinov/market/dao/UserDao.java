package com.dblinov.market.dao;

import com.dblinov.market.entity.Purchase;
import com.dblinov.market.utils.HibernateSessionFactory;
import com.dblinov.market.entity.User;
import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.List;

public class UserDao implements Serializable {
    private static final long serialVersionUID = -1769511858853931581L;
    private static Logger logger = LogManager.getLogger(UserDao.class);
    public User findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String query = "SELECT * FROM users where name = '" + name + "'";
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

    public Purchase findPurchaseById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Purchase.class, id);
    }

    public List<User> findAll() {
        List<User> users = HibernateSessionFactory.getSessionFactory().openSession().createNativeQuery("SELECT * FROM users", User.class).list();
        return users;
    }
}