package fr.esgi.masa.tpcleancode.core.storage;


import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.parser.Parser;
import fr.esgi.masa.tpcleancode.core.utils.FileReader;
import fr.esgi.masa.tpcleancode.core.utils.FileWriter;
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
public class PersistentUsersStorageTest {
    PersistentUsersStorage sut;

    @Mock
    FileReader mockFileReader;

    @Mock
    FileWriter mockFileWriter;

    @Mock
    Parser<User> mockParser;

    @Before
    public void setUp() {
        sut = new PersistentUsersStorage(mockFileReader, mockFileWriter, mockParser);
    }

    @Test
    public void add_shouldReadTextFileOfUserStorageFile() throws Exception {
        var user = new User("toto", UserRole.MEMBER);

        sut.add(user);

        Mockito.verify(mockFileReader).readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH);
    }

    @Test
    public void add_shouldAddNewUserLineToCurrentContentFile() throws Exception {
        var user = new User("toto", UserRole.MEMBER);
        Mockito.when(mockFileReader.readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH))
                .thenReturn("title;LIBRARIAN" + System.lineSeparator());
        var expected = "title;LIBRARIAN" + System.lineSeparator()
                + "toto;MEMBER" + System.lineSeparator();

        sut.add(user);

        Mockito.verify(mockFileWriter).writeContentToFile(expected, PersistentUsersStorage.USERS_STORAGE_FILE_PATH);
    }


    @Test
    public void remove_shouldReadTextFileOfUserStorageFile() throws IOException, IncorrectContentException {
        var user = new User("toto", UserRole.MEMBER);

        sut.remove(user);

        Mockito.verify(mockFileReader).readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH);
    }

    @Test
    public void remove_shouldParseContentFileOfUserStorageToGetListUser() throws IOException, IncorrectContentException {
        var user = new User("tata", UserRole.LIBRARIAN);
        var content = "tata;LIBRARIAN" + System.lineSeparator()
                + "tonton;GUEST" + System.lineSeparator();

        Mockito.when(mockFileReader.readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH))
                .thenReturn(content);

        sut.remove(user);

        Mockito.verify(mockParser).parseList(content);
    }

    @Test
    public void remove_whenContentHasOnlyOneUser_shouldRemoveLastUser() throws IOException, IncorrectContentException {
        var user = new User("tata", UserRole.LIBRARIAN);
        var content = "tata;LIBRARIAN" + System.lineSeparator();
        var listUser = new ArrayList<User>();
        listUser.add(new User("tata", UserRole.LIBRARIAN));

        Mockito.when(mockFileReader.readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH))
                .thenReturn(content);
        Mockito.when(mockParser.parseList(content))
                .thenReturn(listUser);
        sut.remove(user);
        Mockito.verify(mockFileWriter).writeContentToFile("", PersistentUsersStorage.USERS_STORAGE_FILE_PATH);
    }

    @Test
    public void getAll_shouldReadTextFileOfUserStorageFile() throws IOException, IncorrectContentException {
        sut.getAll();

        Mockito.verify(mockFileReader).readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH);
    }

    @Test
    public void getAll_shouldParseListOfUser() throws IOException, IncorrectContentException {
        var content = "login1;GUEST" + System.lineSeparator()
                + "login2;LIBRARIAN" + System.lineSeparator();
        Mockito.when(mockFileReader.readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH))
                .thenReturn(content);
        sut.getAll();

        Mockito.verify(mockParser).parseList(content);
    }

    @Test
    public void getAll_shouldReturnListUser() throws IOException, IncorrectContentException {
        var listUser = new ArrayList<User>();
        listUser.add(new User("login1", UserRole.GUEST));
        listUser.add(new User("login2", UserRole.LIBRARIAN));
        var content = "login1;GUEST" + System.lineSeparator()
                + "login2;LIBRARIAN" + System.lineSeparator();
        Mockito.when(mockFileReader.readTextFile(PersistentUsersStorage.USERS_STORAGE_FILE_PATH))
                .thenReturn(content);
        Mockito.when(mockParser.parseList(content))
                .thenReturn(listUser);

        var result = sut.getAll();

        Assertions.assertThat(result).isEqualTo(listUser);
    }
}