package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import org.junit.Before;
import org.junit.Test;

public class MemoryBooksStorageTest {

    MemoryBooksStorage sut;

    @Before
    public void setUp() {
        sut = new MemoryBooksStorage();
    }

    @Test
    public void addBook_canAddBookWithAllDataPresent() {
        var book = new Book("title book", "toto", "999");

        sut.addBook(book);
    }

    @Test
    public void addBook_throwExceptionIfBookIsNotValid() {

    }
}