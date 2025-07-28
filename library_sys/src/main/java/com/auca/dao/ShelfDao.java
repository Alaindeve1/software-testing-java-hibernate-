package com.auca.dao;

import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.Shelf;
import com.auca.Models.Room;

public class ShelfDao {
    
    Connection connection = new Connection();
    
    public String saveShelf(Shelf shelf) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(shelf);
            transaction.commit();
            session.close();
            return "Shelf saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving shelf: " + e.getMessage();
        }
    }
    
    public Shelf getShelfById(UUID id) {
        try {
            Session session = connection.getSession();
            Shelf shelf = session.get(Shelf.class, id);
            session.close();
            return shelf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Shelf> getAllShelves() {
        try {
            Session session = connection.getSession();
            Query<Shelf> query = session.createQuery("FROM Shelf", Shelf.class);
            List<Shelf> shelves = query.getResultList();
            session.close();
            return shelves;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Shelf> getShelvesByRoom(Room room) {
        try {
            Session session = connection.getSession();
            Query<Shelf> query = session.createQuery("FROM Shelf WHERE room = :room", Shelf.class);
            query.setParameter("room", room);
            List<Shelf> shelves = query.getResultList();
            session.close();
            return shelves;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Shelf> getAvailableShelves() {
        try {
            Session session = connection.getSession();
            Query<Shelf> query = session.createQuery("FROM Shelf WHERE availableStock > 0", Shelf.class);
            List<Shelf> shelves = query.getResultList();
            session.close();
            return shelves;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateShelf(Shelf shelf) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(shelf);
            transaction.commit();
            session.close();
            return "Shelf updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating shelf: " + e.getMessage();
        }
    }
    
    public String deleteShelf(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, id);
            if (shelf != null) {
                session.remove(shelf);
                transaction.commit();
                session.close();
                return "Shelf deleted Successfully";
            } else {
                session.close();
                return "Shelf not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting shelf: " + e.getMessage();
        }
    }
}