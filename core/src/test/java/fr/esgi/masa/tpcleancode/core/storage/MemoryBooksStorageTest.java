package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class MemoryBooksStorageTest {

    MemoryBooksStorage sut;

    @Before
    public void setUp() {
        sut = new MemoryBooksStorage();
    }

    @Test
    public void addBook_whenABookIsValid_shouldAddTheBookInListBook() throws Exception {
        var book = new Book("title book", "toto", "999");
        var beforeAddBookList = sut.getAll();
        Assertions.assertThat(beforeAddBookList.size()).isEqualTo(0);

        sut.add(book);

        var result = sut.getAll();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void addBook_whenABookIsValid_shouldThrowException() {
        var book = new Book("title book", null, "999");
        Assertions.assertThat(book.isValid()).isFalse();

        Assertions.assertThatThrownBy(() -> {
            sut.add(book);
        }).isExactlyInstanceOf(Exception.class)
                .hasMessage("invalid book can't be add");
    }
}