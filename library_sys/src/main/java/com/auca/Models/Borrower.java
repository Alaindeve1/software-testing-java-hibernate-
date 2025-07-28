package com.auca.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "borrower")
public class Borrower {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name = "fine", nullable = false)
    private int fine = 0; // Initialized to zero
    
    @Column(name = "late_charge_fees", nullable = false)
    private int lateChargeFees = 0;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;
    
    // Constructors
    public Borrower() {}
    
    public Borrower(LocalDate pickupDate, LocalDate dueDate, Book book, User reader) {
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
        this.book = book;
        this.reader = reader;
        this.fine = 0;
        this.lateChargeFees = 0;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public LocalDate getPickupDate() {
        return pickupDate;
    }
    
    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public int getFine() {
        return fine;
    }
    
    public void setFine(int fine) {
        this.fine = fine;
    }
    
    public int getLateChargeFees() {
        return lateChargeFees;
    }
    
    public void setLateChargeFees(int lateChargeFees) {
        this.lateChargeFees = lateChargeFees;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public User getReader() {
        return reader;
    }
    
    public void setReader(User reader) {
        this.reader = reader;
    }
    
    // Additional methods for backward compatibility
    public void setBorrowDate(LocalDate borrowDate) {
        this.pickupDate = borrowDate;
    }
    
    public LocalDate getBorrowDate() {
        return this.pickupDate;
    }
    
    // Overloaded setFine method to handle double values
    public void setFine(double fine) {
        this.fine = (int) fine;
    }
}
