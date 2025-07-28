package com.auca.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.auca.util.HibernateUtil;

public class Connection {
    
    public Session getSession() {
        try {
            System.out.println("Getting SessionFactory...");
            SessionFactory factory = HibernateUtil.getSessionFactory();
            
            if (factory == null) {
                throw new RuntimeException("SessionFactory is null!");
            }
            
            System.out.println("Opening session...");
            Session session = factory.openSession();
            System.out.println("Session opened successfully");
            
            return session;
        } catch (Exception e) {
            System.err.println("Error getting session: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}