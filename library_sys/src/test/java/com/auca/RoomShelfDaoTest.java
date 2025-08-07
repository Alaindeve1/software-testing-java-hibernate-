package com.auca;

import com.auca.Models.*;
import com.auca.dao.*;
import org.junit.Before;
import org.junit.Test;
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
        
        // Generate a unique room code for each test run
        String uniqueRoomCode = "ROOM_" + System.currentTimeMillis();
        testRoom = new Room(uniqueRoomCode);
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
        // 1. First, save the room and get its ID
        String roomSaveResult = roomDao.saveRoom(testRoom);
        assertEquals("Room should be saved successfully", "Room saved Successfully", roomSaveResult);
        
        // 2. Get the saved room to ensure it has an ID
        Room savedRoom = roomDao.getRoomByCode(testRoom.getRoomCode());
        assertNotNull("Saved room should not be null", savedRoom);
        
        // 3. Set the saved room to the shelf
        testShelf.setRoom(savedRoom);
        
        // 4. Save the shelf with the saved room reference
        String shelfSaveResult = shelfDao.saveShelf(testShelf);
        assertEquals("Shelf should be saved successfully", "Shelf saved Successfully", shelfSaveResult);
        
        // 5. Get the saved shelf to ensure it has an ID
        Shelf savedShelf = shelfDao.getShelfById(testShelf.getId());
        assertNotNull("Saved shelf should not be null", savedShelf);
        
        // 6. Create a new book with the saved shelf and unique ISBN
        BookDao bookDao = new BookDao();
        String uniqueIsbn = String.valueOf(System.currentTimeMillis()); // Generate unique ISBN
        Book book = new Book("Test Book", "Test Author", uniqueIsbn, "Test Publisher",
                           java.time.LocalDate.now(), 1, BookStatus.AVAILABLE, savedShelf);
        
        // 7. Save the book
        String bookSaveResult = bookDao.saveBook(book);
        assertEquals("Book should be saved successfully", "Book saved Successfully", bookSaveResult);
        
        // 8. Retrieve the book to verify the assignment
        Book savedBook = bookDao.getBookByIsbn(uniqueIsbn);
        assertNotNull("Saved book should not be null", savedBook);
        assertNotNull("Book should be assigned to a shelf", savedBook.getShelf());
        
        // 9. Verify the shelf assignment
        assertEquals("Book should be assigned to the correct shelf", 
                    savedShelf.getId(), savedBook.getShelf().getId());
        
        // 10. Get the shelf with its books using the DAO
        Shelf updatedShelf = shelfDao.getShelfById(savedShelf.getId());
        assertNotNull("Shelf should not be null", updatedShelf);
        
        // 11. Get all books and filter by shelf
        List<Book> allBooks = bookDao.getAllBooks();
        assertNotNull("Books list should not be null", allBooks);
        
        // 12. Check if our book is in the shelf's book list
        boolean bookFound = allBooks.stream()
            .filter(b -> b.getShelf() != null)
            .anyMatch(b -> b.getBookId().equals(savedBook.getBookId()) && 
                         b.getShelf().getId().equals(updatedShelf.getId()));
        assertTrue("Book should be in the shelf's book list", bookFound);
    }
}