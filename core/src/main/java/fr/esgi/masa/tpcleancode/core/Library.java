package fr.esgi.masa.tpcleancode.core;

import fr.esgi.masa.tpcleancode.core.storage.BooksStorage;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.util.List;


public class Library {
    private final BooksStorage booksStorage;

    public Library(BooksStorage booksStorage, Logger logger) {
        this.booksStorage = booksStorage;
    }

    public void start(List<String> arguments) {

    }

    // addBook(Book book, User user)

    // borrowBook(Book book, User user)

    // renderBorrowedBook(Book book, User user)

    // seeContent();
}
