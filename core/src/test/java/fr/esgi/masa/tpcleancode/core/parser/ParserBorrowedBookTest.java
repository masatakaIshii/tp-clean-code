package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
                + "||" + localDate.toString();
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
}