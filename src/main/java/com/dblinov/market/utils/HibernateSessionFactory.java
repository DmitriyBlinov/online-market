package com.dblinov.market.utils;

import com.dblinov.market.entity.Product;
import com.dblinov.market.entity.Purchase;
import com.dblinov.market.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Purchase.class);
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
}