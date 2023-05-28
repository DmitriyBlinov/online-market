package com.dblinov.market.dao.impl;

import com.dblinov.market.dao.ProductDao;
import com.dblinov.market.entity.Product;
import org.hibernate.Session;
import com.dblinov.market.utils.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao, Serializable {
    private static final long serialVersionUID = -1758511858853931581L;
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);

    public Optional<Product> findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.of(session.get(Product.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }

    public void save(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
            logger.info("The Product ID {} was saved", product.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public boolean update(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
            logger.info("The Product ID {} was updated", product.getId());
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return false;
    }

    public void delete(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(product);
            session.getTransaction().commit();
            logger.info("The Product ID {} was deleted", product.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Product> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        criteria.from(Product.class);
        List<Product> products = session.createQuery(criteria).getResultList();
        session.close();
        return products;
    }
}