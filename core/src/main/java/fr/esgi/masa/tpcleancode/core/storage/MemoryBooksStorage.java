package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class MemoryBooksStorage implements Storage<Book> {
    private final List<Book> books;

    public MemoryBooksStorage() {
        books = new ArrayList<>();
    }

    @Override
    public void add(Book book) throws Exception {
        if (!book.isValid()) throw new Exception("invalid book can't be add");
        books.add(book);
    }

    @Override
    public List<Book> getAll() {
        return books;
    }
}
