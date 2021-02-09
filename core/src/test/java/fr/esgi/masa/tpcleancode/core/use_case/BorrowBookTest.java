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
        arguments.add("Michou");
        arguments.add("TitleBook");

        var userList = new ArrayList<User>();
        userList.add(new User("Michelle", UserRole.MEMBER));

        Mockito.when(userStorage.getAll()).thenReturn(userList);

        Assertions.assertThatThrownBy(() -> sut.execute(arguments))
                .isExactlyInstanceOf(NotAuthorizedException.class)
                .hasMessage("The user is not in user list");
    }
}