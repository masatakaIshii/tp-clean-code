package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserBook implements Parser<Book> {

    @Override
    public Book parse(String content) throws IncorrectContentException {
        var bookData = content.split(";");
        checkIfBookDataAreCorrect(bookData);

        var title = bookData[0];
        var authorName = bookData[1];
        var reference = bookData[2];

        return new Book(title, authorName, reference);
    }

    private void checkIfBookDataAreCorrect(String[] bookData) throws IncorrectContentException {
        if (bookData.length != 3) {
            throw new IncorrectContentException("Incorrect number of separator");
        }
        if (isOneDataOfBookDataIsEmpty(bookData)) {
            throw new IncorrectContentException("Miss data in content");
        }
    }

    private boolean isOneDataOfBookDataIsEmpty(String[] bookData) {
        return Arrays.stream(bookData).anyMatch(data -> data.length() == 0);
    }

    @Override
    public List<Book> parseList(String content) throws IncorrectContentException {
        var result = new ArrayList<Book>();
        var listLineBookData = content.split(System.lineSeparator());

        for (String line : listLineBookData) {
            var book = this.parse(line);
            result.add(book);
        }
        return result;
    }
}
