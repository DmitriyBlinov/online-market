package com.dblinov.market.dao;

import com.dblinov.market.entity.Product;
import org.hibernate.Session;
import com.dblinov.market.utils.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class ProductDao implements Serializable {
    private static final long serialVersionUID = -1758511858853931581L;
    private static final Logger logger = LogManager.getLogger(ProductDao.class);
    public Product findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Product.class, id);
    }
    public void save(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
            logger.info("The Product ID {} was saved", product.getId());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

    }
    public boolean update(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        boolean isUpdated = false;
        try {
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
            logger.info("The Product ID {} was updated", product.getId());
            isUpdated = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return isUpdated;
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
            e.printStackTrace();
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