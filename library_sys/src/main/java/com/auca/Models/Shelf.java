package com.auca.Models;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shelf")
public class Shelf {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "initial_stock", nullable = false)
    private int initialStock;
    
    @Column(name = "available_stock", nullable = false)
    private int availableStock;
    
    @Column(name = "borrowed_number", nullable = false)
    private int borrowedNumber;
    
    @Column(name = "book_category", nullable = false)
    private String bookCategory;
    
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
    
    // Constructors
    public Shelf() {}
    
    public Shelf(int initialStock, int availableStock, int borrowedNumber, 
                 String bookCategory, Room room) {
        this.initialStock = initialStock;
        this.availableStock = availableStock;
        this.borrowedNumber = borrowedNumber;
        this.bookCategory = bookCategory;
        this.room = room;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public int getInitialStock() {
        return initialStock;
    }
    
    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }
    
    public int getAvailableStock() {
        return availableStock;
    }
    
    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
    
    public int getBorrowedNumber() {
        return borrowedNumber;
    }
    
    public void setBorrowedNumber(int borrowedNumber) {
        this.borrowedNumber = borrowedNumber;
    }
    
    public String getBookCategory() {
        return bookCategory;
    }
    
    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    // Additional methods for backward compatibility
    public void setCategory(String category) {
        this.bookCategory = category;
    }
    
    public String getCategory() {
        return this.bookCategory;
    }
}