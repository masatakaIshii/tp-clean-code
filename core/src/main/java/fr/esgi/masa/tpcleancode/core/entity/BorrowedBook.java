package fr.esgi.masa.tpcleancode.core.entity;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowedBook {
    private final Book book;
    private final User user;
    private final LocalDate date;

    public BorrowedBook(Book book, User user, LocalDate date) {
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

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "book=" + book +
                ", user=" + user +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedBook that = (BorrowedBook) o;
        return Objects.equals(book, that.book) && Objects.equals(user, that.user) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, user, date);
    }
}
