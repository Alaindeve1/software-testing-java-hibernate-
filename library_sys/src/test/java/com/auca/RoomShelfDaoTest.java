package com.auca;

import com.auca.Models.*;
import com.auca.dao.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.auca.dao.Connection;
import java.util.List;
import java.util.UUID;

public class RoomShelfDaoTest {
    
    private RoomDao roomDao;
    private ShelfDao shelfDao;
    private Room testRoom;
    private Shelf testShelf;
    
    @Before
    public void setUp() {
        roomDao = new RoomDao();
        shelfDao = new ShelfDao();
        
        // Generate a unique room code for each test run
        String uniqueRoomCode = "ROOM_" + System.currentTimeMillis();
        testRoom = new Room(uniqueRoomCode);
        testShelf = new Shelf(101, 75, 15, "theology", testRoom);
    }
    
    @Test
    public void testSaveRoom() {
        String result = roomDao.saveRoom(testRoom);
        assertEquals("Room saved Successfully", result);
    }
    
    @Test
    public void testGetRoomByCode() {
        roomDao.saveRoom(testRoom);
        Room foundRoom = roomDao.getRoomByCode("ROOM_001");
        assertNotNull(foundRoom);
        assertEquals("ROOM_001", foundRoom.getRoomCode());
    }
    
    @Test
    public void testGetAllRooms() {
        roomDao.saveRoom(testRoom);
        List<Room> rooms = roomDao.getAllRooms();
        assertNotNull(rooms);
        assertTrue(rooms.size() > 0);
    }
    
    @Test
    public void testSaveShelf() {
        roomDao.saveRoom(testRoom);
        String result = shelfDao.saveShelf(testShelf);
        assertEquals("Shelf saved Successfully", result);
    }
    
    @Test
    public void testGetShelvesByRoom() {
        roomDao.saveRoom(testRoom);
        testShelf.setRoom(testRoom);
        shelfDao.saveShelf(testShelf);
        
        List<Shelf> roomShelves = shelfDao.getShelvesByRoom(testRoom);
        assertNotNull("Room shelves should not be null", roomShelves);
        assertTrue("Room should have at least one shelf", roomShelves.size() > 0);
    }
    
    @Test
    public void testGetAllShelves() {
        roomDao.saveRoom(testRoom);
        shelfDao.saveShelf(testShelf);
        List<Shelf> shelves = shelfDao.getAllShelves();
        assertNotNull(shelves);
        assertTrue(shelves.size() > 0);
    }
    

    
    /**
     * Helper method to count books in a room using direct HQL query
     */
    private int countBooksInRoomDirectly(UUID roomId) {
        try (Session session = Connection.getSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(b) FROM Book b " +
                "JOIN b.shelf s " +
                "WHERE s.room.roomId = :roomId", Long.class)
                .setParameter("roomId", roomId)
                .uniqueResult();
                
            return count != null ? count.intValue() : 0;
        }
    }
    
}