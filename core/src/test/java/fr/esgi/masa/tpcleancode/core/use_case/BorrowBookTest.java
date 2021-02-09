package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.storage.Storage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class BorrowBookTest {
    BorrowBook sut;

    @Mock
    Storage<Book> bookStorage;

    @Mock
    Storage<BorrowedBook> borrowedBookStorage;

    @Mock
    Storage<User> userStorage;

    @Before
    public void setUp() {
        sut = new BorrowBook(bookStorage, borrowedBookStorage, userStorage);
    }

    @Test
    public void actionNameIsBorrowBook() {
        Assertions.assertThat(sut.actionName()).isEqualTo("borrowBook");
    }

    @Test
    public void whenUserLoginIsNotInFile_shouldThrowException() throws IOException, IncorrectContentException {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");

        var userList = new ArrayList<User>();
        userList.add(new User("Michelle", UserRole.MEMBER));

        Mockito.when(userStorage.getAll()).thenReturn(userList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("The user is not in user list");
    }

    @Test
    public void whenUserIsGuest_shouldThrowException() throws IOException, IncorrectContentException {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");

        var userList = new ArrayList<User>();
        userList.add(new User("Michou", UserRole.GUEST));

        Mockito.when(userStorage.getAll()).thenReturn(userList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("The guest can't borrow book");
    }

    @Test
    public void whenBookNameIsNotInStorage_shouldThrowException() throws IOException, IncorrectContentException {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");

        var userList = new ArrayList<User>();
        userList.add(new User("Michou", UserRole.MEMBER));
        var bookList = new ArrayList<Book>();
        bookList.add(new Book("bookTitle", "author", "987654"));

        Mockito.when(userStorage.getAll()).thenReturn(userList);
        Mockito.when(bookStorage.getAll()).thenReturn(bookList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("The book 'TitleBook' is not available to borrow");
    }

    @Test
    public void whenUserBorrowAlready4Books_shouldThrowException() throws IOException, IncorrectContentException {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");
        var user = new User("Michou", UserRole.MEMBER);
        var userList = new ArrayList<User>();
        userList.add(user);

        var bookList = new ArrayList<Book>();
        bookList.add(new Book("TitleBook", "author", "987654"));

        var borrowedBookList = new ArrayList<BorrowedBook>();
        borrowedBookList.add(new BorrowedBook(new Book("firstBook", "Michou", "123"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("secondBook", "Michou", "456"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("thirdBook", "Michou", "789"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("fourthBook", "Michou", "987"), user, LocalDate.now()));

        Mockito.when(userStorage.getAll()).thenReturn(userList);
        Mockito.when(bookStorage.getAll()).thenReturn(bookList);
        Mockito.when(borrowedBookStorage.getAll()).thenReturn(borrowedBookList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("The user 'Michou' can't borrow more than 4 books");
    }

    @Test
    public void whenAllDataComplete_shouldBorrowedBook() throws Exception {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");
        var user = new User("Michou", UserRole.MEMBER);
        var userList = new ArrayList<User>();
        userList.add(user);

        var bookList = new ArrayList<Book>();
        var book = new Book("TitleBook", "author", "987654");
        bookList.add(book);

        var borrowedBookList = new ArrayList<BorrowedBook>();
        borrowedBookList.add(new BorrowedBook(new Book("firstBook", "Michou", "123"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("secondBook", "Michou", "456"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("thirdBook", "Michou", "789"), user, LocalDate.now()));
        var newBorrowedBook = new BorrowedBook(book, user, LocalDate.now());

        Mockito.when(userStorage.getAll()).thenReturn(userList);
        Mockito.when(bookStorage.getAll()).thenReturn(bookList);
        Mockito.when(borrowedBookStorage.getAll()).thenReturn(borrowedBookList);

        sut.execute(arguments);

        Mockito.verify(borrowedBookStorage).add(newBorrowedBook);
    }

    @Test
    public void whenAllDataComplete_shouldRemoveBookToBookStorage() throws Exception {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");
        var user = new User("Michou", UserRole.MEMBER);
        var userList = new ArrayList<User>();
        userList.add(user);

        var bookList = new ArrayList<Book>();
        var book = new Book("TitleBook", "author", "987654");
        bookList.add(book);

        var borrowedBookList = new ArrayList<BorrowedBook>();
        borrowedBookList.add(new BorrowedBook(new Book("firstBook", "Michou", "123"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("secondBook", "Michou", "456"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("thirdBook", "Michou", "789"), user, LocalDate.now()));
        var newBorrowedBook = new BorrowedBook(book, user, LocalDate.now());

        Mockito.when(userStorage.getAll()).thenReturn(userList);
        Mockito.when(bookStorage.getAll()).thenReturn(bookList);
        Mockito.when(borrowedBookStorage.getAll()).thenReturn(borrowedBookList);

        sut.execute(arguments);

        Mockito.verify(bookStorage).remove(newBorrowedBook.getBook());
    }

    @Test
    public void whenAlreadyBorrowedBook_shouldThrowException() throws Exception {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Michou");
        arguments.add("TitleBook");
        var user = new User("Michou", UserRole.MEMBER);
        var userList = new ArrayList<User>();
        userList.add(user);

        var bookList = new ArrayList<Book>();
        var book = new Book("TitleBook", "author", "987654");
        bookList.add(book);

        var borrowedBookList = new ArrayList<BorrowedBook>();
        borrowedBookList.add(new BorrowedBook(new Book("firstBook", "Michou", "123"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("secondBook", "Michou", "456"), user, LocalDate.now()));
        borrowedBookList.add(new BorrowedBook(new Book("TitleBook", "author", "987654"), user, LocalDate.now()));

        Mockito.when(userStorage.getAll()).thenReturn(userList);
        Mockito.when(bookStorage.getAll()).thenReturn(bookList);
        Mockito.when(borrowedBookStorage.getAll()).thenReturn(borrowedBookList);

        Assertions.assertThatThrownBy(() -> {sut.execute(arguments);})
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("Can't borrow book that you already borrowed");
    }
}
