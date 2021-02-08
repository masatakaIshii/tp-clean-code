package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.utils.FileReader;
import fr.esgi.masa.tpcleancode.core.utils.FileWriter;

import java.util.List;

public class PersistentBooksStorage implements BooksStorage {
    private final String bookFilePath = "bookFile.txt";
    private final String borrowedFilePath = "borrowedFilePath.txt";
    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public PersistentBooksStorage(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    @Override
    public void addBook(Book book) throws Exception {

    }

    @Override
    public List<Book> seeAllBooks() {
        return null;
    }

    @Override
    public void addBorrowBook(BorrowedBook borrowedBook) throws Exception {

    }

    @Override
    public List<BorrowedBook> seeListBorrowedBook() {
        return null;
    }
}
