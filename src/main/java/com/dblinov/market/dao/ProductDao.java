package com.dblinov.market.dao;

import com.dblinov.market.entity.Product;
import org.hibernate.Session;
import com.dblinov.market.utils.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ProductDao {
    public Product findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Product.class, id);
    }
    public void save(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
    public void update(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void delete(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*public Purchase findPurchaseById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Purchase purchase = session.get(Purchase.class, id);
        session.close();
        return purchase;
    }*/

    public List<Product> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        /*HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        criteria.from(Product.class);
        List<Product> products = session.createQuery(criteria).getResultList();*/
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        criteria.from(Product.class);
        List<Product> products = session.createQuery(criteria).getResultList();
        session.close();
        return products;
    }
}