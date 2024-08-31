package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryManagementSystemTest {

    LibraryManagementSystem lms;
    public static final List<Book> availableBooks = LibraryManagementSystem.getAvailableBooks();

    // Note: All the test cases are written in such a way that they can be tested independently and all together also
    // to get the instance of the class before running each unit test
    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
    }

    @Test
    @Order(1)
    public void viewAvailableBooksWhenEmptyLibraryTest() {
        // Redirecting System.out to capture the output for assertions
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Test when no books are available
        lms.viewAvailableBooks();
        String expectedOutput = "Sorry, currently no books are available with us.";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void addBookTest() {
        Book book = new Book("title", "987-123-123-9876", "author", 2004);
        // number of books before adding
        int noOfBooks = availableBooks.size();
        lms.addBook(book);
        assertEquals(noOfBooks + 1, availableBooks.size());
        assertTrue(availableBooks.contains(book));
    }

    @Test
    public void addBookWithEmptyTitleTest() {
        Book book = new Book("", "133-123-123-9875", "author", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with empty title should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullTitleTest() {
        Book book = new Book(null, "132-123-123-9875", "author", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with null title should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithEmptyAuthorTest() {
        Book book = new Book("title", "134-123-123-9875", "", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with empty author name should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullAuthorTest() {
        Book book = new Book("title", "135-123-123-9875", null, 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with null author name should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithFuturePublicationYearTest() {
        Book book = new Book("title", "136-123-123-9875", "author", 2030);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having publication year >" + Year.now() + " should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithVeryOldPublicationYearTest() {
        Book book = new Book("title", "137-123-123-9875", "author", 2);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having publication year < 100 should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullISBNTest() {
        Book book = new Book("title", null, "author", 2000);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having null ISBN should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithImproperLengthISBNTest() {
        Book book = new Book("title", "123", "author", 2000);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having length != 16 should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithDuplicateISBNTest() {
        Book book1 = new Book("title", "987-123-123-9875", "author", 2004);
        Book book2 = new Book("title", "987-123-123-9875", "author", 2004);
        lms.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book2),
                "Adding a book with duplicate ISBN should thrown an IllegalArgumentException");
    }

}