package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryManagementSystemTest {

    private LibraryManagementSystem lms;
    private List<Book> availableBooks;
    private List<Book> borrowedBooks;

    // Note: All the test cases are written in such a way that they can be tested independently and all together also
    // to get the instance of the class before running each unit test
    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
        availableBooks = lms.getAvailableBooks();
        borrowedBooks = lms.getBorrowedBooks();
    }

    @Test
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
    public void viewAvailableBooksTest() {
        // Redirecting System.out to capture the output for assertions
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Add a single books
        lms.addBook(new Book("Atomic Habits", "234-234-234-1255", "James Clear", 2018));
        // Test When one book is available
        lms.viewAvailableBooks();
        String expectedOutput = "Following Books are available with us: \n";
        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(outContent.toString().contains("Title: Atomic Habits"));
        assertTrue(outContent.toString().contains("Author: James Clear"));
        assertTrue(outContent.toString().contains("ISBN: 234-234-234-1255"));
        assertTrue(outContent.toString().contains("PublicationYear: 2018"));
    }

    @Test
    public void addBookTest() {
        Book book = new Book("Ikigai", "987-123-123-9876", "Japanese Guy", 2000);
        lms.addBook(book);
        assertEquals(1, availableBooks.size());
        assertTrue(availableBooks.contains(book));
    }

    @Test
    public void addBookWithEmptyTitleTest() {
        Book book = new Book("", "133-123-123-9875", "James Clear", 2018);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Book title cannot be empty");
        assertEquals("Book title cannot be empty", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithNullTitleTest() {
        Book book = new Book(null, "132-123-123-9875", "James Clear", 2018);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Book title cannot be null!");
        assertEquals("Book title cannot be null!", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithEmptyAuthorTest() {
        Book book = new Book("Atomic Habits", "134-123-123-9875", "", 2018);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Book Author name cannot be empty!");
        assertEquals("Book Author name cannot be empty!", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithNullAuthorTest() {
        Book book = new Book("Atomic Habits", "135-123-123-9875", null, 2018);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Book Author cannot be null!");
        assertEquals("Book Author cannot be null!", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithFuturePublicationYearTest() {
        Book book = new Book("The Alchemist", "136-123-123-9875", "Paulo Coelho", 2030);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Publication year must be between the range of 100 to " + Year.now());
        assertEquals("Publication year must be between the range of 100 to " + Year.now(), exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithVeryOldPublicationYearTest() {
        Book book = new Book("The Alchemist", "137-123-123-9875", "Paulo Coelho", 2);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Cannot add a book having publication year < 100");
        assertEquals("Cannot add a book having publication year < 100", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithNullISBNTest() {
        Book book = new Book("Sapiens: A Brief History of Humankind", null, "Yuval Noah Harari", 2011);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Cannot add a book having null ISBN");
        assertEquals("Cannot add a book having null ISBN", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithImproperLengthISBNTest() {
        Book book = new Book("Sapiens: A Brief History of Humankind", "123", "Yuval Noah Harari", 2011);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Cannot add book having length != 16");
        assertEquals("Cannot add book having length != 16", exceptionThrown.getMessage());
    }

    @Test
    public void addBookWithDuplicateISBNTest() {
        Book book1 = new Book("Sapiens: A Brief History of Humankind", "987-123-123-9875", "Yuval Noah Harari", 2011);
        Book book2 = new Book("Thinking, Fast and Slow", "987-123-123-9875", "Daniel Kahneman", 2011);
        lms.addBook(book1);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.addBook(book2),
                "Cannot add book with duplicate ISBN");
        assertEquals("Cannot add book with duplicate ISBN", exceptionThrown.getMessage());
    }

    @Test
    public void borrowAvailableBookTest() {
        Book book = new Book("The Power of Habit", "987-123-123-9876", "Charles Duhigg", 2012);
        // Add a single book
        lms.addBook(book);
        lms.borrowBook("987-123-123-9876");
        assertEquals(0, availableBooks.size());
        assertEquals(1, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(book));
        assertFalse(availableBooks.contains(book));
    }

    @Test
    public void borrowUnavailableBookTest() {
        Book book = new Book("The Subtle Art of Not Giving a F*ck", "987-123-123-9811", "Mark Manson", 2016);
        // Add a single book
        lms.addBook(book);
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.borrowBook("654-987-987-9812"),
                "Trying to borrow an unavailable book");
        assertEquals("Trying to borrow an unavailable book", exceptionThrown.getMessage());
    }

    @Test
    public void returnBorrowedBookTest() {
        Book book = new Book("Educated", "987-123-123-0000", "Tara Westover", 2018);
        // Add a single book
        lms.addBook(book);
        // borrow that book
        lms.borrowBook("987-123-123-0000");
        // return that borrowed book
        lms.returnBook("987-123-123-0000");
        assertEquals(1, availableBooks.size());
        assertEquals(0, borrowedBooks.size());
        assertTrue(availableBooks.contains(book));
        assertFalse(borrowedBooks.contains(book));
    }

    @Test
    public void returnWrongBookTest() {
        Book book = new Book("The Four Agreements", "987-123-123-1111", "Don Miguel Ruiz", 1997);
        // Add a single book
        lms.addBook(book);
        // borrow an unavailable book
        lms.borrowBook("987-123-123-1111");
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.returnBook("789-789-789-7899"),
                "Trying to return a wrong book which is not yet borrowed");
        assertEquals("Trying to return a wrong book which is not yet borrowed", exceptionThrown.getMessage());
    }

    @Test
    public void borrowMaxTwoBooks() {
        Book book1 = new Book("The Four Agreements", "987-123-123-1118", "Don Miguel Ruiz", 1997);
        Book book2 = new Book("The Four Agreements", "987-123-123-1119", "Don Miguel Ruiz", 1997);
        Book book3 = new Book("The Four Agreements", "987-123-123-1117", "Don Miguel Ruiz", 1997);
        // adding books for borrowing
        lms.addBook(book1);
        lms.addBook(book2);
        lms.addBook(book3);

        lms.borrowBook("987-123-123-1118");
        lms.borrowBook("987-123-123-1119");
        Exception exceptionThrown = assertThrows(IllegalArgumentException.class, () -> lms.borrowBook("987-123-123-1117"),
                "Trying to exceed the maximum limit of allowed borrowed books");
        assertEquals("Trying to exceed the maximum limit of allowed borrowed books",exceptionThrown.getMessage() );
    }
}