package com.auca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.auca.Models.Book;
import com.auca.Models.BookStatus;
import com.auca.Models.Room;
import com.auca.Models.Shelf;
import com.auca.dao.BookDao;
import com.auca.dao.RoomDao;
import com.auca.dao.ShelfDao;

public class BookDaoTest {
    
    private BookDao bookDao;
    private ShelfDao shelfDao;
    private RoomDao roomDao;
    private Book testBook;
    private Shelf testShelf;
    private Room testRoom;
    
    @Before
    public void setUp() {
        bookDao = new BookDao();
        shelfDao = new ShelfDao();
        roomDao = new RoomDao();
        
        // Create test room
        testRoom = new Room();
        testRoom.setRoomCode("TEST_R002");
        roomDao.saveRoom(testRoom);
        
        // Create test shelf
        testShelf = new Shelf();
        testShelf.setBookCategory("Test Category");
        testShelf.setInitialStock(50);
        testShelf.setAvailableStock(50);
        testShelf.setBorrowedNumber(0);
        testShelf.setRoom(testRoom);
        shelfDao.saveShelf(testShelf);
        
        // Create test book
        testBook = new Book();
        testBook.setTitle("Test Java Programming");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("978-0123456789");
        testBook.setPublicationYear(2023);
        testBook.setEdition(1);
        testBook.setStatus(BookStatus.AVAILABLE);
        testBook.setShelf(testShelf);
    }
    
    @Test
    public void testSaveBook() {
        String result = bookDao.saveBook(testBook);
        assertEquals("Book saved Successfully", result);
    }
    
    @Test
    public void testGetBookByIsbn() {
        bookDao.saveBook(testBook);
        Book retrievedBook = bookDao.getBookByIsbn("978-0123456789");
        assertNotNull("Book should be retrieved", retrievedBook);
        assertEquals("Test Java Programming", retrievedBook.getTitle());
        assertEquals("Test Author", retrievedBook.getAuthor());
    }
    
    @Test
    public void testGetAllBooks() {
        bookDao.saveBook(testBook);
        java.util.List<Book> allBooks = bookDao.getAllBooks();
        assertNotNull("Books list should not be null", allBooks);
        assertTrue("Should have at least one book", allBooks.size() > 0);
    }
    
    @Test
    public void testSearchBooksByTitle() {
        bookDao.saveBook(testBook);
        java.util.List<Book> searchResults = bookDao.searchBooksByTitle("Java");
        assertNotNull("Search results should not be null", searchResults);
        assertTrue("Should find the test book", searchResults.size() > 0);
    }
    
    @Test
    public void testSearchBooksByAuthor() {
        bookDao.saveBook(testBook);
        java.util.List<Book> searchResults = bookDao.searchBooksByAuthor("Test Author");
        assertNotNull("Search results should not be null", searchResults);
        assertTrue("Should find the test book", searchResults.size() > 0);
    }
}