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
    public void add(Book element) throws Exception {
        var content = this.fileReader.readTextFile(booksStorageFilePath);
        var newContent = content + getContentFormatOfOneBook(element);
        this.fileWriter.writeContentToFile(newContent, booksStorageFilePath);
    }

    @Override
    public void remove(Book element) throws IOException, IncorrectContentException {
        var content = this.fileReader.readTextFile(booksStorageFilePath);
        var listBook = this.parserBook.parseList(content);
        listBook.remove(element);
        var newContent = getContentFormatOfListBook(listBook);
        this.fileWriter.writeContentToFile(newContent, booksStorageFilePath);
    }

    private String getContentFormatOfListBook(List<Book> listBook) {
        StringBuilder content = new StringBuilder();

        listBook.forEach(book -> {
            content.append(getContentFormatOfOneBook(book));
        });
        return content.toString();
    }

    private String getContentFormatOfOneBook(Book book) {
        return book.getTitle() + ";" + book.getAuthorName() + ";" + book.getReference()
                + System.lineSeparator();
    }

    @Override
    public List<Book> getAll() throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(booksStorageFilePath);

        return parserBook.parseList(content);
    }
}
