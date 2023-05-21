package com.dblinov.market.dao.impl;

import com.dblinov.market.dao.PurchaseDao;
import com.dblinov.market.entity.Product;
import com.dblinov.market.entity.Purchase;
import com.dblinov.market.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class PurchaseDaoImpl implements PurchaseDao,Serializable {
    private static final long serialVersionUID = -1768511858853931581L;
    private static final Logger logger = LogManager.getLogger(PurchaseDaoImpl.class);

    public Optional<Purchase> findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.of(session.get(Purchase.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public void save(Purchase purchase) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(purchase);
            session.getTransaction().commit();
            logger.info("The Purchase ID {} was saved", purchase.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
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
            logger.info("The Purchase ID {} was updated", purchase.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
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
            logger.info("The Purchase ID {} was deleted", purchase.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Purchase> getAllPurchases() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Purchase> criteria = builder.createQuery(Purchase.class);
        criteria.from(Purchase.class);
        List<Purchase> purchases = session.createQuery(criteria).getResultList();
        session.close();
        return purchases;
    }
}