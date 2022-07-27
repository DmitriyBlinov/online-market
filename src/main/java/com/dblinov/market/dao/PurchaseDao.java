package com.dblinov.market.dao;

import com.dblinov.market.entity.Purchase;
import com.dblinov.market.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class PurchaseDao {
    public Purchase findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Purchase.class, id);
    }
    public void save(Purchase purchase) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(purchase);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
    public void update(Purchase purchase) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(purchase);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void delete(Purchase purchase) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(purchase);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Purchase> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteria = builder.createQuery(Purchase.class);
        criteria.from(Purchase.class);
        List<Purchase> purchases = session.createQuery(criteria).getResultList();
        session.close();
        return purchases;
    }
}
