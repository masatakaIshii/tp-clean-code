package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;

import java.util.ArrayList;
import java.util.List;

public class MemoryBooksStorage implements BooksStorage {
    private final List<BorrowedBook> listBorrowedBook;
    private final List<Book> books;

    public MemoryBooksStorage() {
        listBorrowedBook = new ArrayList<>();
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
    public void addBorrowBook(BorrowedBook borrowedBook) throws Exception {
        var book = borrowedBook.getBook();
        if (!book.isValid()) throw new Exception("invalid book can't be add");
        listBorrowedBook.add(borrowedBook);
    }

    @Override
    public List<BorrowedBook> seeListBorrowedBook() {
        return listBorrowedBook;
    }
}
