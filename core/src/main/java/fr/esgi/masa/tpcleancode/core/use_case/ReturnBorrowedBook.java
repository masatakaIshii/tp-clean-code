package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBorrowedBooksStorage;

import java.util.List;

public class ReturnBorrowedBook implements LibraryAction {
    public ReturnBorrowedBook(PersistentBooksStorage booksStorage, PersistentBorrowedBooksStorage borrowBooksStorage, AddBook addBook) {

    }

    @Override
    public String actionName() {
        return "returnBorrowedBook";
    }

    @Override
    public void execute(List<String> arguments) {

    }
}
