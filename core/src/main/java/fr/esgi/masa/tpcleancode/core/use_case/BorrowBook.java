package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.util.List;

public class BorrowBook implements LibraryAction {
    public BorrowBook(Storage<Book> booksStorage, Storage<BorrowedBook> borrowBooksStorage, Storage<User> userStorage) {

    }

    @Override
    public String actionName() {
        return "borrowBook";
    }

    @Override
    public void execute(List<String> arguments) {

    }
}
