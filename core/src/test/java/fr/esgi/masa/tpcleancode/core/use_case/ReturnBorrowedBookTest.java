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
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReturnBorrowedBookTest {
    ReturnBorrowedBook sut;

    @Mock
    Storage<Book> mockBookStorage;

    @Mock
    Storage<BorrowedBook> mockBorrowedBookStorage;

    @Mock
    Storage<User> mockUserStorage;

    @Before
    public void setUp() {
        sut = new ReturnBorrowedBook(mockBookStorage, mockBorrowedBookStorage, mockUserStorage);

    }

    @Test
    public void actionNameIsReturnBorrowedBook() {
        Assertions.assertThat(sut.actionName()).isEqualTo("returnBorrowedBook");
    }

    @Test
    public void whenArgumentsNotEnough_shouldThrowException() {
        List<String> arguments = new ArrayList<>();
        arguments.add("returnBorrowedBook");
        arguments.add("Gertrude");

        Assertions.assertThatThrownBy(() -> {
            sut.execute(arguments);
        })
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBorrowedBookOnArgumentNoPresent_shouldThrowException() throws IOException, IncorrectContentException {
        List<String> arguments = new ArrayList<>();
        arguments.add("returnBorrowedBook");
        arguments.add("Gertrude");
        arguments.add("TitleBorrowedBook");

        var borrowedBook = new BorrowedBook(
                new Book("title1", "authorName1", "oijoijo"),
                new User("test", UserRole.MEMBER),
                LocalDate.now()
        );
        var borrowedList = new ArrayList<BorrowedBook>();
        borrowedList.add(borrowedBook);
        Mockito.when(mockBorrowedBookStorage.getAll()).thenReturn(borrowedList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("Not borrowed book is present");
    }

    @Test
    public void whenDataCorrect_shouldAddBookStorage() throws Exception {
        List<String> arguments = new ArrayList<>();
        arguments.add("returnBorrowedBook");
        arguments.add("Gertrude");
        arguments.add("TitleBorrowedBook");
        var book = new Book("TitleBorrowedBook", "authorName1", "oijoijo");
        var user = new User("Gertrude", UserRole.MEMBER);
        var borrowedBook = new BorrowedBook(
                book,
                user,
                LocalDate.now()
        );
        var borrowedList = new ArrayList<BorrowedBook>();
        borrowedList.add(borrowedBook);
        Mockito.when(mockBorrowedBookStorage.getAll()).thenReturn(borrowedList);

        sut.execute(arguments);

        Mockito.verify(mockBookStorage).add(book);
    }

    @Test
    public void whenDataCorrect_shouldRemoveBorrowedBook() throws Exception {
        List<String> arguments = new ArrayList<>();
        arguments.add("returnBorrowedBook");
        arguments.add("Gertrude");
        arguments.add("TitleBorrowedBook");
        var book = new Book("TitleBorrowedBook", "authorName1", "oijoijo");
        var user = new User("Gertrude", UserRole.MEMBER);
        var borrowedBook = new BorrowedBook(
                book,
                user,
                LocalDate.now()
        );
        var borrowedList = new ArrayList<BorrowedBook>();
        borrowedList.add(borrowedBook);
        Mockito.when(mockBorrowedBookStorage.getAll()).thenReturn(borrowedList);

        sut.execute(arguments);

        Mockito.verify(mockBorrowedBookStorage).remove(borrowedBook);
    }
}