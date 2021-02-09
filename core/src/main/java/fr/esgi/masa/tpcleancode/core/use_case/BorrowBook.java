package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBorrowedBooksStorage;

import java.util.List;

public class BorrowBook implements LibraryAction{
    public BorrowBook(PersistentBooksStorage booksStorage, PersistentBorrowedBooksStorage borrowBooksStorage, AddBook addBook) {

    }

    @Override
    public String actionName() {
        return "borrowBook";
    }

    @Override
    public void execute(List<String> arguments) {

    }
}
