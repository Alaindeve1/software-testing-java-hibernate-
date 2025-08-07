package com.auca.dao;

import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.Room;
import com.auca.Models.Shelf;

public class RoomDao {
    
    Connection connection = new Connection();
    
    public String saveRoom(Room room) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(room);
            transaction.commit();
            session.close();
            return "Room saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving room: " + e.getMessage();
        }
    }
    
    public Room getRoomById(UUID id) {
        try {
            Session session = connection.getSession();
            Room room = session.get(Room.class, id);
            session.close();
            return room;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Room getRoomByCode(String code) {
        try {
            Session session = connection.getSession();
            Query<Room> query = session.createQuery("FROM Room WHERE roomCode = :code", Room.class);
            query.setParameter("code", code);
            Room room = query.uniqueResult();
            session.close();
            return room;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Room> getAllRooms() {
        try {
            Session session = connection.getSession();
            Query<Room> query = session.createQuery("FROM Room", Room.class);
            List<Room> rooms = query.getResultList();
            session.close();
            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateRoom(Room room) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(room);
            transaction.commit();
            session.close();
            return "Room updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating room: " + e.getMessage();
        }
    }
    
    public String deleteRoom(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.remove(room);
                transaction.commit();
                session.close();
                return "Room deleted Successfully";
            } else {
                session.close();
                return "Room not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting room: " + e.getMessage();
        }
    }
    
    public int countBooksInRoom(UUID roomId) {
        try {
            Session session = connection.getSession();
            
           
            Query<Long> query = session.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.shelf.room.roomId = :roomId", 
                Long.class
            );
            query.setParameter("roomId", roomId);
            Long count = query.uniqueResult();
            
            session.close();
            return count != null ? count.intValue() : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}