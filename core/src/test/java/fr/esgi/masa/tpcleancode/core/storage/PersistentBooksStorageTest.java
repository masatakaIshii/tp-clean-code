package fr.esgi.masa.tpcleancode.core.storage;


import fr.esgi.masa.tpcleancode.core.entity.Book;
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
public class PersistentBooksStorageTest {
    PersistentBooksStorage sut;

    @Mock
    FileReader mockFileReader;

    @Mock
    FileWriter mockFileWriter;

    @Mock
    Parser<Book> mockParser;

    @Before
    public void setUp() {
        sut = new PersistentBooksStorage(mockFileReader, mockFileWriter, mockParser);
    }

    @Test
    public void addBook_shouldReadTextFileOfBookFile() throws Exception {
        var book = new Book("toto", "toto", "toto");

        sut.add(book);

        Mockito.verify(mockFileReader).readTextFile(PersistentBooksStorage.booksStorageFilePath);
    }

    @Test
    public void addBook_shouldAddNewBookLineToCurrentContentFile() throws Exception {
        var book = new Book("toto", "tata", "titi");

        var currentContentOfFile = "Les misérable;Victor Hugo;654f6z5ef"
                + System.lineSeparator()
                + "Test;test;oizj65"
                + System.lineSeparator();

        var expectedContentOfFile = currentContentOfFile
                + "toto;tata;titi"
                + System.lineSeparator();
        Mockito.when(mockFileReader.readTextFile(PersistentBooksStorage.booksStorageFilePath))
                .thenReturn(currentContentOfFile);

        sut.add(book);

        Mockito.verify(mockFileWriter).writeContentToFile(expectedContentOfFile, PersistentBooksStorage.booksStorageFilePath);
    }

    @Test
    public void setAllBooks_shouldReadTextFileOfBookFile() throws IOException, IncorrectContentException {
        sut.getAll();

        Mockito.verify(mockFileReader).readTextFile(PersistentBooksStorage.booksStorageFilePath);
    }

    @Test
    public void setAllBooks_shouldParseListOfBook() throws IOException, IncorrectContentException {
        Mockito.when(mockFileReader.readTextFile(PersistentBooksStorage.booksStorageFilePath))
                .thenReturn("toto;tata;titi");
        sut.getAll();

        Mockito.verify(mockParser).parseList("toto;tata;titi");
    }

    @Test
    public void setAllBooks_shouldReturnListOfBook() throws IOException, IncorrectContentException {
        var expectedBookList = new ArrayList<Book>();
        expectedBookList.add(new Book("toto", "tata", "titi"));
        Mockito.when(mockFileReader.readTextFile(PersistentBooksStorage.booksStorageFilePath))
                .thenReturn("toto;tata;titi");
        Mockito.when(mockParser.parseList("toto;tata;titi"))
                .thenReturn(expectedBookList);

        var result = sut.getAll();

        Assertions.assertThat(result).isEqualTo(expectedBookList);
    }
}