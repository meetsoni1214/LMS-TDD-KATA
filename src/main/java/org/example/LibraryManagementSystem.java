package org.example;

import org.example.model.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryManagementSystem {
    private static final List<Book> availableBooks = new ArrayList<>();

    // Declaring this methods so that other classes can only access the list in a read-only manner
    public static List<Book> getAvailableBooks() {
        return Collections.unmodifiableList(availableBooks);
    }

    public void addBook(Book book) throws IllegalArgumentException {
        if (validateTitle(book.getTitle()) && validateAuthor(book.getAuthor()) && validatePublicationYear(book.getPublicationYear())
        && validateISBN(book.getISBN())) {
            availableBooks.add(book);
            System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
        }
    }

    private boolean validateTitle(String title) throws IllegalArgumentException{
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Book title cannot be empty or null!");
        return true;
    }
    private boolean validateAuthor(String author) throws IllegalArgumentException {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("Book Author cannot be empty or null!");
        return true;
    }
    private boolean validatePublicationYear(int year) {
        // considering there are no books in the library older than this year ;)
        if (year > Year.now().getValue() || year < 100) throw new IllegalArgumentException("Publication year must be between the range of 100 to " + Year.now());
        return true;
    }
    private boolean validateISBN(String ISBN) throws IllegalArgumentException {
        if (ISBN == null || ISBN.length() != 16) throw new IllegalArgumentException("ISBN cannot be null or it must have length = 16!");
        return true;
    }
}
