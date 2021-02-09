package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.util.List;

public class ReturnBorrowedBook implements LibraryAction {
    public ReturnBorrowedBook(Storage<Book> booksStorage, Storage<BorrowedBook> borrowBooksStorage, Storage<User> userStorage) {

    }

    @Override
    public String actionName() {
        return "returnBorrowedBook";
    }

    @Override
    public void execute(List<String> arguments) {

    }
}
