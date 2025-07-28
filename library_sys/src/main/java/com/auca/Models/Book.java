package com.auca.Models;

//import com.auca.Models.BookStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "author", nullable = false)
    private String author;
    
    @Column(name = "isbn_code", nullable = false, unique = true)
    private String isbnCode;
    
    @Column(name = "publisher_name", nullable = false)
    private String publisherName;
    
    @Column(name = "publication_year", nullable = false)
    private LocalDate publicationYear;
    
    @Column(name = "edition", nullable = false)
    private int edition;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;
    
    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Borrower> borrowers;
    
    // Constructors
    public Book() {}
    
    public Book(String title, String author, String isbnCode, String publisherName, LocalDate publicationYear, 
                int edition, BookStatus bookStatus, Shelf shelf) {
        this.title = title;
        this.author = author;
        this.isbnCode = isbnCode;
        this.publisherName = publisherName;
        this.publicationYear = publicationYear;
        this.edition = edition;
        this.bookStatus = bookStatus;
        this.shelf = shelf;
    }
    
    // Getters and Setters
    public UUID getBookId() {
        return bookId;
    }
    
    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIsbnCode() {
        return isbnCode;
    }
    
    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }
    
    public String getPublisherName() {
        return publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public LocalDate getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(LocalDate publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    // Overloaded method for int parameter (year)
    public void setPublicationYear(int year) {
        this.publicationYear = LocalDate.of(year, 1, 1);
    }
    
    public int getEdition() {
        return edition;
    }
    
    public void setEdition(int edition) {
        this.edition = edition;
    }
    
    public BookStatus getBookStatus() {
        return bookStatus;
    }
    
    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
    
    public Shelf getShelf() {
        return shelf;
    }
    
    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
    
    public List<Borrower> getBorrowers() {
        return borrowers;
    }
    
    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }
    
    // Additional methods for backward compatibility
    public void setIsbn(String isbn) {
        this.isbnCode = isbn;
    }
    
    public String getIsbn() {
        return this.isbnCode;
    }
    
    public void setStatus(BookStatus status) {
        this.bookStatus = status;
    }
    
    public BookStatus getStatus() {
        return this.bookStatus;
    }
}
