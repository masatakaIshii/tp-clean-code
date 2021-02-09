package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
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
    public void execute(List<String> arguments) throws Exception {
        var userLogin = arguments.get(1);
        var bookTitle = arguments.get(2);
        var foundUser = searchUserByLoginName(userLogin);
        var foundBook = searchBookByTitle(bookTitle);
        var borrowedBookList = borrowedBookStorage.getAll();

        checkIfNumberBorrowedBooksLimitExceed(foundUser, borrowedBookList);

        var newBorrowedBook = new BorrowedBook(foundBook, foundUser, LocalDate.now());
        checkIfUserAlreadyBorrowedBook(borrowedBookList, newBorrowedBook);

        borrowedBookStorage.add(newBorrowedBook);
        bookStorage.remove(foundBook);
    }

    private void checkIfNumberBorrowedBooksLimitExceed(User foundUser, List<BorrowedBook> borrowedBookList) throws NotAuthorizedException {
        var checked = borrowedBookList.stream()
                .filter(borrowedBook -> borrowedBook.getUser().equals(foundUser))
                .count();

        if (checked >= 4) throw new NotAuthorizedException(
                "The user '" + foundUser.getLogin() + "' can't borrow more than 4 books"
        );
    }

    private void checkIfUserAlreadyBorrowedBook(List<BorrowedBook> borrowedBookList, BorrowedBook newBorrowedBook) throws NotAuthorizedException {
        var numberEqualBorrowedBook = borrowedBookList.stream()
                .filter(borrowedBook -> borrowedBook.equals(newBorrowedBook))
                .count();
        if (numberEqualBorrowedBook > 0) throw new NotAuthorizedException("Can't borrow book that you already borrowed");
    }

    private Book searchBookByTitle(String bookTitle) throws NotAuthorizedException, IOException, IncorrectContentException {
        var bookList = bookStorage.getAll();
        var foundBook = findBookByTitle(bookTitle, bookList);
        checkIfBookIfAvailable(bookTitle, foundBook);

        return foundBook;
    }

    private Book findBookByTitle(String bookTitle, List<Book> bookList) {
        return bookList.stream()
                .filter(book -> book.getTitle().equals(bookTitle))
                .findFirst()
                .orElse(null);
    }

    private void checkIfBookIfAvailable(String bookTitle, Book foundBook) throws NotAuthorizedException {
        if (foundBook == null) throw new NotAuthorizedException(
                "The book '" + bookTitle + "' is not available to borrow"
        );
    }

    private User searchUserByLoginName(String userLogin) throws IOException, IncorrectContentException, NotAuthorizedException {
        var userList = userStorage.getAll();
        var foundUser = findUserByLogin(userLogin, userList);

        checkIfFoundUserIsAuthorizedToBorrowedBook(foundUser);

        return foundUser;
    }

    private void checkIfFoundUserIsAuthorizedToBorrowedBook(User searchedUser) throws NotAuthorizedException {
        if (searchedUser == null) throw new NotAuthorizedException("The user is not in user list");
        if (searchedUser.getRole() == UserRole.GUEST) throw new NotAuthorizedException("The guest can't borrow book");
    }

    private User findUserByLogin(String userLogin, List<User> userList) {
        return userList.stream()
                .filter(user -> user.getLogin().equals(userLogin))
                .findFirst().orElse(null);
    }
}
