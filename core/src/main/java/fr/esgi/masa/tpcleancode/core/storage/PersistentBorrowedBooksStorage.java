package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.parser.Parser;
import fr.esgi.masa.tpcleancode.core.utils.FileReader;
import fr.esgi.masa.tpcleancode.core.utils.FileWriter;

import java.io.IOException;
import java.util.List;

public class PersistentBorrowedBooksStorage implements Storage<BorrowedBook> {
    public final static String BORROWED_BOOKS_STORAGE_FILE_PATH = "borrowedBooksStorageFile.txt";
    private final FileReader fileReader;
    private final FileWriter fileWriter;
    private final Parser<BorrowedBook> parserBorrowedBook;

    public PersistentBorrowedBooksStorage(FileReader fileReader, FileWriter fileWriter, Parser<BorrowedBook> parserBorrowedBook) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.parserBorrowedBook = parserBorrowedBook;
    }

    @Override
    public void add(BorrowedBook element) throws Exception {
        var content = fileReader.readTextFile(BORROWED_BOOKS_STORAGE_FILE_PATH);
        var newContent = content + getContentOfNewBorrowedBook(element);
        fileWriter.writeContentToFile(newContent, BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    private String getContentOfNewBorrowedBook(BorrowedBook borrowedBook) {
        var book = borrowedBook.getBook();
        var user = borrowedBook.getUser();
        return book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + "||" + user.getLogin() + ";" + user.getRole()
                + "||" + borrowedBook.getDate()
                + System.lineSeparator();
    }

    @Override
    public void remove(BorrowedBook element) throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(BORROWED_BOOKS_STORAGE_FILE_PATH);
        var listBorrowedBook = parserBorrowedBook.parseList(content);

        listBorrowedBook.remove(element);
        var newContent = getContentListBorrowedBook(listBorrowedBook);

        fileWriter.writeContentToFile(newContent, BORROWED_BOOKS_STORAGE_FILE_PATH);
    }

    private String getContentListBorrowedBook(List<BorrowedBook> listBorrowedBook) {
        var content = new StringBuilder();
        listBorrowedBook.forEach(borrowedBook -> {
            content.append(getContentOfNewBorrowedBook(borrowedBook));
        });
        return content.toString();
    }

    @Override
    public List<BorrowedBook> getAll() throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(BORROWED_BOOKS_STORAGE_FILE_PATH);

        return parserBorrowedBook.parseList(content);
    }
}
