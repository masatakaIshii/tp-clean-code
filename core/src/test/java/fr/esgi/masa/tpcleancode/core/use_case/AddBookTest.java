package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.storage.Storage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class AddBookTest {
    AddBook sut;

    @Mock
    Storage<Book> bookStorage;

    @Mock
    Storage<User> userStorage;

    @Before
    public void setUp() {
        sut = new AddBook(bookStorage, userStorage);
    }

    @Test
    public void actionNameIsAddBook() {
        Assertions.assertThat(sut.actionName()).isEqualTo("addBook");
    }

    @Test
    public void whenNumberArgumentsNotCorrespond_shouldThrowException() {
        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add("Gertrude");

        Assertions.assertThatThrownBy(()-> {
            sut.execute(arguments);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenUserIsNotLibrarian_shouldThrowException() throws Exception {
        var userLogin = "notLibrarian";
        var bookTitle = "newTitle";
        var bookAuthor = "newAuthor";
        var reference = "newRef";

        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add(userLogin);
        arguments.add(bookTitle);
        arguments.add(bookAuthor);
        arguments.add(reference);

        var usersList = new ArrayList<User>();
        usersList.add(new User("LibrarianLogin", UserRole.LIBRARIAN));
        usersList.add(new User("notLibrarian", UserRole.MEMBER));

        Mockito.when(userStorage.getAll()).thenReturn(usersList);

        Assertions.assertThatThrownBy(() -> {sut.execute(arguments);})
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("Only librarian can add book");
    }

    @Test
    public void whenUserIsLibrarianAndDataCorrect_shouldAddBook() throws Exception {
        var userLogin = "LibrarianLogin";
        var bookTitle = "newTitle";
        var bookAuthor = "newAuthor";
        var reference = "newRef";

        var arguments = new ArrayList<String>();
        arguments.add(sut.actionName());
        arguments.add(userLogin);
        arguments.add(bookTitle);
        arguments.add(bookAuthor);
        arguments.add(reference);

        var usersList = new ArrayList<User>();
        usersList.add(new User("LibrarianLogin", UserRole.LIBRARIAN));

        var booksList = new ArrayList<Book>();

        Mockito.when(userStorage.getAll()).thenReturn(usersList);
        Mockito.when(bookStorage.getAll()).thenReturn(booksList);

        sut.execute(arguments);

        Mockito.verify(bookStorage).add(new Book(bookTitle, bookAuthor, reference));
    }
}