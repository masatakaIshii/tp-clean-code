package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.storage.Storage;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.io.IOException;
import java.util.List;

public class SeeContentEveryUser implements SeeContent {

    private final Storage<Book> booksStorage;
    private final Logger logger;

    public SeeContentEveryUser(Storage<Book> booksStorage, Logger logger) {
        this.booksStorage = booksStorage;
        this.logger = logger;
    }

    @Override
    public void execute() {
        try {
            var listBooks = booksStorage.getAll();

            logger.out("-----List books-----");

            showAllBooks(listBooks);
        } catch (IOException | IncorrectContentException e) {
            e.printStackTrace();
        }
    }

    private void showAllBooks(List<Book> listBooks) {

        listBooks.forEach(book -> logger.out(
                "title: " + book.getTitle() + System.lineSeparator() +
                        "author: " + book.getAuthorName() + System.lineSeparator() +
                        "reference: " + book.getReference() + System.lineSeparator()
        ));
    }
}
