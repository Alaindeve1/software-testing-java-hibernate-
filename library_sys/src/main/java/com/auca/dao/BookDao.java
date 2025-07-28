package com.auca.dao;

import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.auca.Models.Book;
import com.auca.Models.BookStatus;

public class BookDao {
    
    Connection connection = new Connection();
    
    public String saveBook(Book book) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            session.close();
            return "Book saved Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving book: " + e.getMessage();
        }
    }
    
    public Book getBookById(UUID id) {
        try {
            Session session = connection.getSession();
            Book book = session.get(Book.class, id);
            session.close();
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Book getBookByIsbn(String isbn) {
        try {
            Session session = connection.getSession();
            Query<Book> query = session.createQuery("FROM Book WHERE isbnCode = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            Book book = query.uniqueResult();
            session.close();
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Book> getAllBooks() {
        try {
            Session session = connection.getSession();
            Query<Book> query = session.createQuery("FROM Book", Book.class);
            List<Book> books = query.getResultList();
            session.close();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Book> getBooksByStatus(BookStatus status) {
        try {
            Session session = connection.getSession();
            Query<Book> query = session.createQuery("FROM Book WHERE bookStatus = :status", Book.class);
            query.setParameter("status", status);
            List<Book> books = query.getResultList();
            session.close();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Book> searchBooksByTitle(String title) {
        try {
            Session session = connection.getSession();
            Query<Book> query = session.createQuery("FROM Book WHERE title LIKE :title", Book.class);
            query.setParameter("title", "%" + title + "%");
            List<Book> books = query.getResultList();
            session.close();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        try {
            Session session = connection.getSession();
            Query<Book> query = session.createQuery("FROM Book WHERE author LIKE :author", Book.class);
            query.setParameter("author", "%" + author + "%");
            List<Book> books = query.getResultList();
            session.close();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateBook(Book book) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
            session.close();
            return "Book updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating book: " + e.getMessage();
        }
    }
    
    public String deleteBook(UUID id) {
        try {
            Session session = connection.getSession();
            Transaction transaction = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.remove(book);
                transaction.commit();
                session.close();
                return "Book deleted Successfully";
            } else {
                session.close();
                return "Book not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting book: " + e.getMessage();
        }
    }
}