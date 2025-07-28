package com.auca.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import com.auca.Models.*;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                System.out.println("=== Starting Hibernate Configuration ===");
                
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                
                properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
                properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/auca_lib");
                properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
                properties.put(Environment.JAKARTA_JDBC_PASSWORD, "@Masengesho1");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                
                System.out.println("Database URL: " + properties.get(Environment.JAKARTA_JDBC_URL));
                System.out.println("Database User: " + properties.get(Environment.JAKARTA_JDBC_USER));
                
                configuration.setProperties(properties);
                
                System.out.println("Adding annotated classes...");
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Location.class);
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Borrower.class);
                configuration.addAnnotatedClass(MembershipType.class);
                configuration.addAnnotatedClass(Membership.class);
                configuration.addAnnotatedClass(Room.class);
                configuration.addAnnotatedClass(Shelf.class);
                System.out.println("Classes added successfully");
                
                System.out.println("Building ServiceRegistry...");
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                System.out.println("ServiceRegistry built successfully");
                
                System.out.println("Building SessionFactory...");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("=== SessionFactory created successfully! ===");
            }
        } catch (Exception ex) {
            System.err.println("=== ERROR: Failed to create SessionFactory ===");
            System.err.println("Error message: " + ex.getMessage());
            System.err.println("Error class: " + ex.getClass().getName());
            ex.printStackTrace();
            throw new RuntimeException("Failed to create SessionFactory", ex);
        }
        return sessionFactory;
    }
}