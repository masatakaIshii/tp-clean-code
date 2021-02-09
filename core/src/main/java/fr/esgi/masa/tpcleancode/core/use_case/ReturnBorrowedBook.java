package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.io.IOException;
import java.util.List;

public class ReturnBorrowedBook implements LibraryAction {
    private final Storage<Book> bookStorage;
    private final Storage<BorrowedBook> borrowedBookStorage;
    private final Storage<User> userStorage;

    public ReturnBorrowedBook(Storage<Book> booksStorage, Storage<BorrowedBook> borrowBooksStorage, Storage<User> userStorage) {
        this.bookStorage = booksStorage;
        this.borrowedBookStorage = borrowBooksStorage;
        this.userStorage = userStorage;
    }

    @Override
    public String actionName() {
        return "returnBorrowedBook";
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
        if (arguments.size() != 3) throw new IllegalArgumentException();

        var userLogin = arguments.get(1);
        var borrowedBookTitle = arguments.get(2);
        var listBorrowedBook = borrowedBookStorage.getAll();

        var foundBorrowedBook = searchBorrowedBook(userLogin, borrowedBookTitle, listBorrowedBook);
        bookStorage.add(foundBorrowedBook.getBook());
        borrowedBookStorage.remove(foundBorrowedBook);
    }

    private BorrowedBook searchBorrowedBook(String userLogin, String borrowedBookTitle, List<BorrowedBook> listBorrowedBook) throws NotAuthorizedException {
        var foundBorrowedBook = listBorrowedBook.stream()
                .filter(borrowedBook -> {
                    return borrowedBook.getUser().getLogin().equals(userLogin)
                            && borrowedBook.getBook().getTitle().equals(borrowedBookTitle);
                })
                .findFirst()
                .orElse(null);
        if (foundBorrowedBook == null) throw new NotAuthorizedException("Not borrowed book is present");
        return foundBorrowedBook;
    }
}
