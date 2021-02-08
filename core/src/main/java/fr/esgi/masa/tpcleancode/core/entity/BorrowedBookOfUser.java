package fr.esgi.masa.tpcleancode.core.entity;

import java.time.LocalDate;

public class BorrowedBookOfUser {
    private final Book book;
    private final User user;
    private final LocalDate date;

    public BorrowedBookOfUser(Book book, User user, LocalDate date) {
        this.book = book;
        this.user = user;
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }
}
