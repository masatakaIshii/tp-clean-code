package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;

import java.util.List;

public class MemoryBooksStorage implements Storage {
    private List<Book> books;
    @Override
    public void addBook(Book book) {

    }

    @Override
    public List<String> seeContent() {
        return null;
    }
}
