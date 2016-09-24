package com.partofa.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 * Created by tust on 23.09.2016.
 */

public class HibernateUtil {

    private static HibernateUtil instance = new HibernateUtil();

    private SessionFactory sessionFactory;

    private HibernateUtil(){
        this.sessionFactory = buildSessionFactory();
    }


    private synchronized static SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        if(instance == null){
            return new HibernateUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}


