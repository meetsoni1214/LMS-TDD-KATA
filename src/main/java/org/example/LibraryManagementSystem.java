package org.example;

import org.example.model.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LibraryManagementSystem {
    private final List<Book> availableBooks = new ArrayList<>();
    private final List<Book> borrowedBooks = new ArrayList<>();

    // Declaring this methods so that other classes can only access the list in a read-only manner
    public List<Book> getAvailableBooks() {
        return Collections.unmodifiableList(availableBooks);
    }
    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }
    public void viewAvailableBooks() {
        if (availableBooks.isEmpty()) {
            System.out.println("Sorry, currently no books are available with us.");
            return;
        }
        System.out.println("Following Books are available with us: \n");
        for (Book book : availableBooks) {
            System.out.println(
                    "Title: " + book.getTitle() + "\n"
                            + "Author: " + book.getAuthor() + "\n"
                            + "PublicationYear: " + book.getPublicationYear() + "\n"
                            + "ISBN: " + book.getISBN() + "\n");
        }
    }
    // Note: In addBook user should not able to add book with duplicate ISBN as in assessment it is specified that ISBN is a unique property of book.
    public void addBook(Book book) throws IllegalArgumentException {
        if (validateTitle(book.getTitle()) && validateAuthor(book.getAuthor()) && validatePublicationYear(book.getPublicationYear())
                && validateISBN(book.getISBN())) {
            availableBooks.add(book);
            System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
        }
    }
    public void borrowBook(String ISBN) throws IllegalArgumentException {

        if (borrowedBooks.size() == 2) {
            throw new IllegalArgumentException("Trying to exceed the maximum limit of allowed borrowed books");
        }

        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = availableBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                borrowedBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " borrowed Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("Trying to borrow an unavailable book");
    }
    public void returnBook(String ISBN) throws IllegalArgumentException {
        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = borrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                availableBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " returned Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("Trying to return a wrong book which is not yet borrowed");
    }
    private boolean validateTitle(String title) throws IllegalArgumentException {
        if (title == null)
            throw new IllegalArgumentException("Book title cannot be null!");
        else if (title.isEmpty())
            throw new IllegalArgumentException("Book title cannot be empty");
        return true;
    }
    private boolean validateAuthor(String author) throws IllegalArgumentException {
        if (author == null)
            throw new IllegalArgumentException("Book Author cannot be null!");
        else if (author.isEmpty())
            throw new IllegalArgumentException("Book Author name cannot be empty!");
        return true;
    }
    private boolean validatePublicationYear(int year) {
        // considering there are no books in the library older than this year ;)
        if (year > Year.now().getValue())
            throw new IllegalArgumentException("Publication year must be between the range of 100 to " + Year.now());
        else if (year < 100)
            throw new IllegalArgumentException("Cannot add a book having publication year < 100");

        return true;
    }
    private boolean validateISBN(String ISBN) throws IllegalArgumentException {
        if (ISBN == null)
            throw new IllegalArgumentException("Cannot add a book having null ISBN");
        else if (ISBN.length() != 16)
            throw new IllegalArgumentException("Cannot add book having length != 16");

        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(ISBN)) {
                throw new IllegalArgumentException("Cannot add book with duplicate ISBN");
            }
        }
        return true;
    }
}
