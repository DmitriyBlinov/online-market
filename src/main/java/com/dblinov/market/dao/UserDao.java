package com.dblinov.market.dao;

import com.dblinov.market.entity.Purchase;
import com.dblinov.market.utils.HibernateSessionFactory;
import com.dblinov.market.entity.User;
import org.hibernate.Session;

import java.util.List;

public class UserDao {
    public User findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByName(String name) {
        String query = "SELECT * FROM users where name = '" + name + "'";
        List<User> users = HibernateSessionFactory.getSessionFactory()
                .openSession()
                .createNativeQuery(query, User.class)
                .list();
        if (users.isEmpty()) {
            return null;
        }
        User user = users.get(0);
        return user;
    }

    public void save(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    public Purchase findPurchaseById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Purchase.class, id);
    }


    public List<User> findAll() {
        List<User> users = HibernateSessionFactory.getSessionFactory().openSession().createNativeQuery("SELECT * FROM users", User.class).list();
        return users;
    }
}