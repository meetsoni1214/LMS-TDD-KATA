package org.example;

import org.example.model.Book;

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
        availableBooks.add(book);
        System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
    }

}
