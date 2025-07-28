package com.auca.dao;

import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.Borrower;
import com.auca.Models.Book;
import com.auca.Models.User;

public class BorrowerDao {
    
    Connection connection = new Connection();
    
    public String saveBorrower(Borrower borrower) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(borrower);
            transaction.commit();
            session.close();
            return "Borrower record saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving borrower: " + e.getMessage();
        }
    }
    
    public Borrower getBorrowerById(UUID id) {
        try {
            Session session = connection.getSession();
            Borrower borrower = session.get(Borrower.class, id);
            session.close();
            return borrower;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Borrower> getAllBorrowers() {
        try {
            Session session = connection.getSession();
            Query<Borrower> query = session.createQuery("FROM Borrower", Borrower.class);
            List<Borrower> borrowers = query.getResultList();
            session.close();
            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Borrower> getBorrowersByUser(User user) {
        try {
            Session session = connection.getSession();
            Query<Borrower> query = session.createQuery("FROM Borrower WHERE reader = :user", Borrower.class);
            query.setParameter("user", user);
            List<Borrower> borrowers = query.getResultList();
            session.close();
            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Borrower> getBorrowersByBook(Book book) {
        try {
            Session session = connection.getSession();
            Query<Borrower> query = session.createQuery("FROM Borrower WHERE book = :book", Borrower.class);
            query.setParameter("book", book);
            List<Borrower> borrowers = query.getResultList();
            session.close();
            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Borrower> getActiveBorrowings() {
        try {
            Session session = connection.getSession();
            Query<Borrower> query = session.createQuery("FROM Borrower WHERE returnDate IS NULL", Borrower.class);
            List<Borrower> borrowers = query.getResultList();
            session.close();
            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Borrower> getOverdueBorrowings() {
        try {
            Session session = connection.getSession();
            Query<Borrower> query = session.createQuery(
                "FROM Borrower WHERE returnDate IS NULL AND dueDate < CURRENT_DATE", 
                Borrower.class
            );
            List<Borrower> borrowers = query.getResultList();
            session.close();
            return borrowers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateBorrower(Borrower borrower) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(borrower);
            transaction.commit();
            session.close();
            return "Borrower record updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating borrower: " + e.getMessage();
        }
    }
    
    public String deleteBorrower(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            Borrower borrower = session.get(Borrower.class, id);
            if (borrower != null) {
                session.remove(borrower);
                transaction.commit();
                session.close();
                return "Borrower record deleted Successfully";
            } else {
                session.close();
                return "Borrower record not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting borrower: " + e.getMessage();
        }
    }
}