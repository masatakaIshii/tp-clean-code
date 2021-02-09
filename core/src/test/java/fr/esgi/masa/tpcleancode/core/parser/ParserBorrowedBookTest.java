package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ParserBorrowedBookTest {
    ParserBorrowedBook sut;

    @Before
    public void setUp() {
        sut = new ParserBorrowedBook();
    }

    @Test
    public void parse_contentWithCompleteData_shouldReturnBorrowedBook() throws IncorrectContentException {
        var book = new Book("titlebook", "authorName", "refeoij25");
        var user = new User("login", UserRole.GUEST);
        var localDate = LocalDate.now();
        var content = book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";GUEST"
                + "||" + localDate.toString()
                + System.lineSeparator();
        var result = sut.parse(content);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getBook()).isEqualTo(book);
        Assertions.assertThat(result.getUser()).isEqualTo(user);
        Assertions.assertThat(result.getDate()).isEqualTo(localDate);
    }

    @Test
    public void parse_contentWithIncompleteBookData_shouldThrowIncorrectContentException() {
        var book = new Book("titlebook", "authorName", "refeoij25");
        var user = new User("login", UserRole.GUEST);
        var localDate = LocalDate.now();
        var content = book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";GUEST"
                + "||" + localDate.toString();

        Assertions.assertThatThrownBy(() -> sut.parse(content))
                .isExactlyInstanceOf(IncorrectContentException.class)
                .hasMessage("Incorrect book data");
    }

    @Test
    public void parse_contentWithIncompleteUserData_shouldThrowIncorrectContentException() {
        var book = new Book("titlebook", "authorName", "refeoij25");
        var user = new User("login", UserRole.GUEST);
        var localDate = LocalDate.now();
        var content = book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin()
                + "||" + localDate.toString();

        Assertions.assertThatThrownBy(() -> sut.parse(content))
                .isExactlyInstanceOf(IncorrectContentException.class)
                .hasMessage("Incorrect user data");
    }

    @Test
    public void parse_contentWithMissingData_shouldThrowIncorrectContentException() {
        var book = new Book("titlebook", "authorName", "refeoij25");
        var user = new User("login", UserRole.GUEST);
        var localDate = LocalDate.now();
        var content = book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";MEMBER"
                + "||";

        Assertions.assertThatThrownBy(() -> sut.parse(content))
                .isExactlyInstanceOf(IncorrectContentException.class)
                .hasMessage("Missing data");
    }

    @Test
    public void parsList_fewLinesCorrectContent_shouldReturnListBorrowedBook() throws IncorrectContentException {
        var book = new Book("titlebook", "authorName", "refeoij25");
        var user = new User("login", UserRole.GUEST);
        var localDate = LocalDate.now();
        var book2 = new Book("titlebook2", "authorName2", "refeoij2q");
        var user2 = new User("login2", UserRole.GUEST);
        var localDate2 = LocalDate.now();
        var content = book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";GUEST"
                + "||" + localDate.toString()
                + System.lineSeparator()
                + book2.getTitle() + ";" + book2.getAuthorName() + ";" + book2.getReference()
                + "||" + user2.getLogin() + ";GUEST"
                + "||" + localDate2.toString()
                + System.lineSeparator();
        var listBorrowedBook = new ArrayList<BorrowedBook>();
        listBorrowedBook.add(new BorrowedBook(book, user, localDate));
        listBorrowedBook.add(new BorrowedBook(book2, user2, localDate2));

        var result = sut.parseList(content);

        Assertions.assertThat(result).isEqualTo(listBorrowedBook);
    }
}