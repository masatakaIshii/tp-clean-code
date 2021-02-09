package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.io.IOException;
import java.util.List;

public class BorrowBook implements LibraryAction {
    private final Storage<Book> bookStorage;
    private final Storage<BorrowedBook> borrowedBookStorage;
    private final Storage<User> userStorage;

    public BorrowBook(Storage<Book> booksStorage, Storage<BorrowedBook> borrowBooksStorage, Storage<User> userStorage) {
        this.bookStorage = booksStorage;
        this.borrowedBookStorage = borrowBooksStorage;
        this.userStorage = userStorage;
    }

    @Override
    public String actionName() {
        return "borrowBook";
    }

    @Override
    public void execute(List<String> arguments) throws IOException, IncorrectContentException, NotAuthorizedException {
        var userLogin = arguments.get(1);
        var userList = userStorage.getAll();

        var searchedUser = userList.stream()
                .filter(user -> user.getLogin().equals(userLogin))
                .findFirst().orElse(null);
        if (searchedUser == null) throw new NotAuthorizedException("The user is not in user list");


    }
}
