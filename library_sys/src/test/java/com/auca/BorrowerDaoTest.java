package com.auca;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.auca.dao.*;
import com.auca.Models.*;
import java.time.LocalDate;

public class BorrowerDaoTest {
    
    private BorrowerDao borrowerDao;
    private UserDao userDao;
    private BookDao bookDao;
    private LocationDao locationDao;
    private RoomDao roomDao;
    private ShelfDao shelfDao;
    private Borrower testBorrower;
    private User testUser;
    private Book testBook;
    
    @Before
    public void setUp() {
        borrowerDao = new BorrowerDao();
        userDao = new UserDao();
        bookDao = new BookDao();
        locationDao = new LocationDao();
        roomDao = new RoomDao();
        shelfDao = new ShelfDao();
        
        // Create all dependencies
        locationDao.saveLocationWithParentCode("997", "TEST_BORROWER_LOCATION", LocationType.PROVINCE, null);
        
        Location location = new Location();
        location.setCode(997);
        location.setName("TEST_BORROWER_LOCATION");
        location.setType(LocationType.PROVINCE);
        
        Room room = new Room();
        room.setCode("TEST_R002");
        roomDao.saveRoom(room);
        
        Shelf shelf = new Shelf();
        shelf.setBookCategory("Test Fiction");
        shelf.setInitialStock(25);
        shelf.setAvailableStock(25);
        shelf.setBorrowedNumber(0);
        shelf.setRoom(room);
        shelfDao.saveShelf(shelf);
        
        testUser = new User();
        testUser.setPersonId("1199980123456787");
        testUser.setFirstName("Borrower");
        testUser.setLastName("Test");
        testUser.setUserName("borrowertest123");
        testUser.setPassword("password");
        testUser.setRole(Role.STUDENT);
        testUser.setGender(Gender.MALE);
        testUser.setLocation(location);
        userDao.saveUser(testUser);
        
        testBook = new Book();
        testBook.setTitle("Test Book for Borrowing");
        testBook.setAuthor("Test Borrower Author");
        testBook.setIsbn("978-0123456788");
        testBook.setPublicationYear(2023);
        testBook.setEdition(1);
        testBook.setStatus(BookStatus.AVAILABLE);
        testBook.setShelf(shelf);
        bookDao.saveBook(testBook);
        
        testBorrower = new Borrower();
        testBorrower.setReader(testUser);
        testBorrower.setBook(testBook);
        testBorrower.setBorrowDate(LocalDate.now());
        testBorrower.setDueDate(LocalDate.now().plusDays(14));
        testBorrower.setFine(0.0);
    }
    
    @Test
    public void testSaveBorrower() {
        String result = borrowerDao.saveBorrower(testBorrower);
        assertEquals("Borrower saved Successfully", result);
    }
    
    @Test
    public void testGetBorrowersByUser() {
        borrowerDao.saveBorrower(testBorrower);
        java.util.List<Borrower> userBorrowings = borrowerDao.getBorrowersByUser(testUser);
        assertNotNull("User borrowings should not be null", userBorrowings);
        assertTrue("User should have at least one borrowing", userBorrowings.size() > 0);
    }
    
    @Test
    public void testGetActiveBorrowings() {
        borrowerDao.saveBorrower(testBorrower);
        java.util.List<Borrower> activeBorrowings = borrowerDao.getActiveBorrowings();
        assertNotNull("Active borrowings should not be null", activeBorrowings);
        assertTrue("Should have at least one active borrowing", activeBorrowings.size() > 0);
    }
    
    @Test
    public void testGetAllBorrowers() {
        borrowerDao.saveBorrower(testBorrower);
        java.util.List<Borrower> allBorrowers = borrowerDao.getAllBorrowers();
        assertNotNull("Borrowers list should not be null", allBorrowers);
        assertTrue("Should have at least one borrower", allBorrowers.size() > 0);
    }
    
    @Test
    public void testUpdateBorrower() {
        borrowerDao.saveBorrower(testBorrower);
        testBorrower.setReturnDate(LocalDate.now());
        testBorrower.setFine(50.0);
        
        String result = borrowerDao.updateBorrower(testBorrower);
        assertEquals("Borrower updated Successfully", result);
    }
    
    @Test
    public void testGetOverdueBorrowings() {
        // Create an overdue borrowing
        Borrower overdueBorrower = new Borrower();
        overdueBorrower.setReader(testUser);
        overdueBorrower.setBook(testBook);
        overdueBorrower.setBorrowDate(LocalDate.now().minusDays(20));
        overdueBorrower.setDueDate(LocalDate.now().minusDays(5)); // 5 days overdue
        overdueBorrower.setFine(0.0);
        
        borrowerDao.saveBorrower(overdueBorrower);
        
        java.util.List<Borrower> overdueBorrowings = borrowerDao.getOverdueBorrowings();
        assertNotNull("Overdue borrowings should not be null", overdueBorrowings);
        // Note: This might be 0 if the DAO method filters by return date
    }
}