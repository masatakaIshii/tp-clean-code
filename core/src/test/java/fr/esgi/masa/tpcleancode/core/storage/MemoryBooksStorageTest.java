package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class MemoryBooksStorageTest {

    MemoryBooksStorage sut;

    @Before
    public void setUp() {
        sut = new MemoryBooksStorage();
    }

    @Test
    public void addBook_whenABookIsValid_shouldAddTheBookInListBook() throws Exception {
        var book = new Book("title book", "toto", "999");
        var beforeAddBookList = sut.seeAllBooks();
        Assertions.assertThat(beforeAddBookList.size()).isEqualTo(0);

        sut.addBook(book);

        var result = sut.seeAllBooks();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void addBook_whenABookIsValid_shouldThrowException() {
        var book = new Book("title book", null, "999");
        Assertions.assertThat(book.isValid()).isFalse();

        Assertions.assertThatThrownBy(() -> {
            sut.addBook(book);
        }).isExactlyInstanceOf(Exception.class)
                .hasMessage("invalid book can't be add");
    }

    @Test
    public void addBook_whenABookWithAllDataPresent_nothingHappened() throws Exception {
        var book = new Book("title book", "toto", "999");
        var member = new User("tata", UserRole.MEMBER);
        var borrowedBookOfUser = new BorrowedBook(book, member, LocalDate.now());

        Assertions.assertThat(book.isValid()).isTrue();
        sut.addBorrowBook(borrowedBookOfUser);
    }

    @Test
    public void addBook_whenBookIsNotValid_shouldThrowException() {
        var book = new Book(null, "toto", "000");
        var member = new User("guest", UserRole.MEMBER);
        var borrowedBookOfUser = new BorrowedBook(book, member, LocalDate.now());

        Assertions.assertThat(book.isValid()).isFalse();
        Assertions.assertThatThrownBy(() -> {
            sut.addBorrowBook(borrowedBookOfUser);
        })
                .isExactlyInstanceOf(Exception.class)
                .hasMessage("invalid book can't be add");
    }
}