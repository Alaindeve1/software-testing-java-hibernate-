package com.auca;

import com.auca.Models.*;
import com.auca.dao.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        testRoom = new Room("ROOM_001");
        testShelf = new Shelf(100, 95, 5, "Computer Science", testRoom);
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
    
    @Test
    public void testCountBooksInRoom() {
        // Save the room and shelf first
        roomDao.saveRoom(testRoom);
        shelfDao.saveShelf(testShelf);
        
        // Create and save some books on the shelf
        BookDao bookDao = new BookDao();
        Book book1 = new Book("Java Programming", "alain", "1234567890", "Tech Books", 
                             java.time.LocalDate.now(), 1, BookStatus.AVAILABLE, testShelf);
        Book book2 = new Book("Python Basics", "kevin", "0987654321", "Tech Books", 
                             java.time.LocalDate.now(), 1, BookStatus.AVAILABLE, testShelf);
        
        // Save the books
        bookDao.saveBook(book1);
        bookDao.saveBook(book2);
        
        // Test the count
        int bookCount = roomDao.countBooksInRoom(testRoom.getRoomId());
        
        // Verify the count is correct
        assertTrue("Book count should be greater than 0", bookCount > 0);
        assertTrue("Book count should be at least 2", bookCount >= 2);
    }
    
    @Test
    public void testAssignBookToShelf() {
        // 1. First, save the room and shelf
        roomDao.saveRoom(testRoom);
        shelfDao.saveShelf(testShelf);
        
        // 2. Create a new book
        BookDao bookDao = new BookDao();
        Book book = new Book("Test Book", "Test Author", "1234567890123", "Test Publisher",
                           java.time.LocalDate.now(), 1, BookStatus.AVAILABLE, testShelf);
        
        // 3. Save the book (this assigns it to the shelf)
        String saveResult = bookDao.saveBook(book);
        assertEquals("Book should be saved successfully", "Book saved Successfully", saveResult);
        
        // 4. Retrieve the book to verify the assignment
        Book savedBook = bookDao.getBookByIsbn("1234567890123");
        assertNotNull("Saved book should not be null", savedBook);
        assertNotNull("Book should be assigned to a shelf", savedBook.getShelf());
        assertEquals("Book should be assigned to the correct shelf", 
                    testShelf.getId(), savedBook.getShelf().getId());
        
        // 5. Verify the book is in the shelf's book list
        Shelf updatedShelf = shelfDao.getShelfById(testShelf.getId());
        assertNotNull("Shelf should have books", updatedShelf.getBooks());
        assertFalse("Shelf's book list should not be empty", updatedShelf.getBooks().isEmpty());
        
        // 6. Check if our book is in the shelf's book list
        boolean bookFound = updatedShelf.getBooks().stream()
            .anyMatch(b -> b.getBookId().equals(savedBook.getBookId()));
        assertTrue("Book should be in the shelf's book list", bookFound);
    }
}