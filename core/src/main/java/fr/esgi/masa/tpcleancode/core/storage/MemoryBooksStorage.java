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
    public void add(Book element) throws Exception {
        if (!element.isValid()) throw new Exception("invalid book can't be add");
        books.add(element);
    }

    @Override
    public void remove(Book element) {

    }

    @Override
    public List<Book> getAll() {
        return books;
    }
}
