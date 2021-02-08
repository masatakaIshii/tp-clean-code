package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBookOfUser;

import java.util.ArrayList;
import java.util.List;

public class MemoryBooksStorage implements Storage {
    private final List<BorrowedBookOfUser> listBorrowedBookOfUser;
    private final List<Book> books;

    public MemoryBooksStorage() {
        listBorrowedBookOfUser = new ArrayList<>();
        books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) throws Exception {
        if (!book.isValid()) throw new Exception("invalid book can't be add");
        books.add(book);
    }

    @Override
    public List<Book> seeAllBooks() {
        return books;
    }

    @Override
    public void addBorrowBook(BorrowedBookOfUser borrowedBookOfUser) throws Exception {
        var book = borrowedBookOfUser.getBook();
        if (!book.isValid()) throw new Exception("invalid book can't be add");
        listBorrowedBookOfUser.add(borrowedBookOfUser);
    }

    @Override
    public List<BorrowedBookOfUser> seeListBorrowedBookOfUser() {
        return listBorrowedBookOfUser;
    }
}
