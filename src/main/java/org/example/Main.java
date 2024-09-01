package org.example;

import org.example.model.Book;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        System.out.println("Welcome to Library Management System!");
        lms.viewAvailableBooks();
        try {
            lms.addBook(new Book("Ikigai", "123-123-123-1233", "Japanese Guy", 2000));
            lms.addBook(new Book("Atomic Habits", "234-234-234-2344", "James Clear", 2018));
            lms.addBook(new Book("The Alchemist", "345-345-345-3455", "Paulo Coelho", 1988));
            lms.addBook(new Book("Sapiens: A Brief History of Humankind", "456-456-456-4566", "Yuval Noah Harari", 2011));
            lms.addBook(new Book("Thinking, Fast and Slow", "567-567-567-5677", "Daniel Kahneman", 2011));
            lms.addBook(new Book("The Power of Habit", "678-678-678-6788", "Charles Duhigg", 2012));
            lms.addBook(new Book("The Subtle Art of Not Giving a F*ck", "789-789-789-7899", "Mark Manson", 2016));
            lms.addBook(new Book("Educated", "890-890-890-8900", "Tara Westover", 2018));
            lms.addBook(new Book("The Four Agreements", "901-901-901-9011", "Don Miguel Ruiz", 1997));
            lms.addBook(new Book("The Lean Startup", "012-012-012-0122", "Eric Ries", 2011));
            lms.addBook(new Book("Rich Dad Poor Dad", "123-234-345-4567", "Robert T. Kiyosaki", 1997));

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        lms.viewAvailableBooks();
        try {
            lms.borrowBook("012-012-012-0122");
            lms.borrowBook("890-890-890-8900");
            lms.returnBook("890-890-890-8900");
            lms.returnBook("012-012-012-0122");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}