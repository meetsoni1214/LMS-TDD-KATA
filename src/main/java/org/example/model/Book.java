package org.example.model;

public class Book {
    String title;
    String ISBN;
    String author;
    int publicationYear;

    public Book(String title, String ISBN, String author, int publicationYear) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
