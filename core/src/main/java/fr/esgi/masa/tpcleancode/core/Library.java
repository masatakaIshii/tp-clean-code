package fr.esgi.masa.tpcleancode.core;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.storage.Storage;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.util.List;


public class Library {
    private final Storage<Book> booksStorage;

    public Library(Storage<Book> booksStorage, Logger logger) {
        this.booksStorage = booksStorage;
    }

    public void start(List<String> arguments) {

    }

    // addBook(Book book, User user)

    // borrowBook(Book book, User user)

    // renderBorrowedBook(Book book, User user)

    // seeContent();
}
