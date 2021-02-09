package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
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
import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class PersistentBorrowedBooksStorageTest {
    PersistentBorrowedBooksStorage sut;

    @Mock
    FileReader mockFileReader;

    @Mock
    FileWriter mockFileWriter;

    @Mock
    Parser<BorrowedBook> mockParse;

    @Before
    public void setUp() {
        sut = new PersistentBorrowedBooksStorage(mockFileReader, mockFileWriter, mockParse);
    }

    @Test
    public void add_shouldReadTextFile() throws Exception {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var borrowedBook = new BorrowedBook(book, user, LocalDate.now());
        sut.add(borrowedBook);

        Mockito.verify(mockFileReader).readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    @Test
    public void add_shouldWriteContentToFile() throws Exception {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var localDate = LocalDate.now();
        var borrowedBook = new BorrowedBook(book, user, localDate);
        var expectedContent = book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";LIBRARIAN"
                + "||" + localDate.toString()
                + System.lineSeparator();
        Mockito.when(mockFileReader.readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH))
                .thenReturn("");

        sut.add(borrowedBook);

        Mockito.verify(mockFileWriter)
                .writeContentToFile(
                        expectedContent,
                        PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    @Test
    public void remove_shouldReadTextFile() throws IOException, IncorrectContentException {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var borrowedBook = new BorrowedBook(book, user, LocalDate.now());
        sut.remove(borrowedBook);

        Mockito.verify(mockFileReader).readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    @Test
    public void remove_shouldParseContentToListBorrowedBooks() throws IOException, IncorrectContentException {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var localDate = LocalDate.now();
        var borrowedBook = new BorrowedBook(book, user, localDate);

        Mockito.when(mockFileReader.readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH))
                .thenReturn("title;authorName;9879870||login;LIBRARIAN||" + localDate.toString() + System.lineSeparator());

        sut.remove(borrowedBook);

        Mockito.verify(mockParse).parseList("title;authorName;9879870||login;LIBRARIAN||" + localDate.toString() + System.lineSeparator());
    }

    @Test
    public void remove_shouldRemoveConcernedBorrowedBook() throws IOException, IncorrectContentException {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var localDate = LocalDate.now();
        var borrowedBook = new BorrowedBook(book, user, localDate);

        var book2 = new Book("title2", "authorName2", "0000");
        var user2 = new User("login2", UserRole.MEMBER);
        var localDate2 = LocalDate.now().minusDays(5);
        var borrowedBook2 = new BorrowedBook(book2, user2, localDate2);

        var listBorrowedBooks = new ArrayList<BorrowedBook>();
        listBorrowedBooks.add(borrowedBook);
        listBorrowedBooks.add(borrowedBook2);
        var contentFile = "title;authorName;9879870||login;LIBRARIAN||" + localDate.toString() + System.lineSeparator()
                + "title2;authorName2;0000||login2;MEMBER||" + localDate2.toString() + System.lineSeparator();

        var expectedContent = "title2;authorName2;0000||login2;MEMBER||" + localDate2.toString() + System.lineSeparator();

        Mockito.when(mockFileReader.readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH))
                .thenReturn(contentFile);
        Mockito.when(mockParse.parseList(contentFile)).thenReturn(listBorrowedBooks);

        sut.remove(borrowedBook);

        Mockito.verify(mockFileWriter)
                .writeContentToFile(
                        expectedContent,
                        PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    @Test
    public void getAll_shouldReadTextFile() throws IOException, IncorrectContentException {
        sut.getAll();

        Mockito.verify(mockFileReader).readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    @Test
    public void getAll_shouldParseContentFile() throws IOException, IncorrectContentException {
        var localDate = LocalDate.now();
        var contentFile = "title;authorName;9879870||login;LIBRARIAN||" + localDate.toString() + System.lineSeparator();

        Mockito.when(mockFileReader.readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH))
        .thenReturn(contentFile);

        sut.getAll();

        Mockito.verify(mockParse).parseList(contentFile);
    }

    @Test
    public void getAll_shouldReturnListBorrowedBook() throws IOException, IncorrectContentException {
        var book = new Book("title", "authorName", "9879870");
        var user = new User("login", UserRole.LIBRARIAN);
        var localDate = LocalDate.now();
        var borrowedBook = new BorrowedBook(book, user, localDate);

        var book2 = new Book("title2", "authorName2", "0000");
        var user2 = new User("login2", UserRole.MEMBER);
        var localDate2 = LocalDate.now().minusDays(5);
        var borrowedBook2 = new BorrowedBook(book2, user2, localDate2);

        var listBorrowedBooks = new ArrayList<BorrowedBook>();
        listBorrowedBooks.add(borrowedBook);
        listBorrowedBooks.add(borrowedBook2);
        var contentFile = "title;authorName;9879870||login;LIBRARIAN||" + localDate.toString() + System.lineSeparator()
                + "title2;authorName2;0000||login2;MEMBER||" + localDate2.toString() + System.lineSeparator();

        Mockito.when(mockFileReader.readTextFile(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH))
                .thenReturn(contentFile);
        Mockito.when(mockParse.parseList(contentFile))
        .thenReturn(listBorrowedBooks);

        var result = sut.getAll();

        Assertions.assertThat(result).isEqualTo(listBorrowedBooks);
    }
}