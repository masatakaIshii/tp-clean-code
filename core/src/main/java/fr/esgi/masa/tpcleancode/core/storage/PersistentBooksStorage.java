package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.parser.Parser;
import fr.esgi.masa.tpcleancode.core.utils.FileReader;
import fr.esgi.masa.tpcleancode.core.utils.FileWriter;

import java.io.IOException;
import java.util.List;

public class PersistentBooksStorage implements Storage<Book> {
    public final static String booksStorageFilePath = "booksStorageFile.txt";
    private final FileReader fileReader;
    private final FileWriter fileWriter;
    private final Parser<Book> parserBook;

    public PersistentBooksStorage(FileReader fileReader, FileWriter fileWriter, Parser<Book> parserBook) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.parserBook = parserBook;
    }

    @Override
    public void add(Book book) throws Exception {
        var content = this.fileReader.readTextFile(booksStorageFilePath);
        var newContent = getContentWithNewBook(book, content);
        this.fileWriter.writeContentToFile(newContent, booksStorageFilePath);
    }

    private String getContentWithNewBook(Book book, String content) {
        return content
                + book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + System.lineSeparator();
    }

    @Override
    public List<Book> getAll() throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(booksStorageFilePath);

        return parserBook.parseList(content);
    }
}
