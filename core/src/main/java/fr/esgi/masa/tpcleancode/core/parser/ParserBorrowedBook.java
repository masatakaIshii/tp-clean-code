package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParserBorrowedBook implements Parser<BorrowedBook> {
    private final Map<String, UserRole> mapUserRole;

    public ParserBorrowedBook() {
        mapUserRole = new HashMap<>();
        mapUserRole.put("GUEST", UserRole.GUEST);
        mapUserRole.put("MEMBER", UserRole.MEMBER);
        mapUserRole.put("LIBRARIAN", UserRole.LIBRARIAN);
    }

    @Override
    public BorrowedBook parse(String content) throws IncorrectContentException {
        var propertiesContent = content.split(Pattern.quote("||"));
        if (propertiesContent.length != 3) throw new IncorrectContentException("Missing data");
        var book = getBookByContent(propertiesContent[0]);
        var user = getUserByContent(propertiesContent[1]);
        var localDateWithoutLineSeparator = removeLineSeparator(propertiesContent[2]);
        var localDate = LocalDate.parse(localDateWithoutLineSeparator);

        return new BorrowedBook(book, user, localDate);
    }

    private String removeLineSeparator(String propertyContent) {
        if (propertyContent.contains(System.lineSeparator())) {
            return propertyContent.replaceFirst(System.lineSeparator(), "");
        }
        return propertyContent;
    }

    private Book getBookByContent(String bookContent) throws IncorrectContentException {
        var bookData = bookContent.split(";");
        if (bookData.length != 3) throw new IncorrectContentException("Incorrect book data");
        var title = bookData[0];
        var authorName = bookData[1];
        var reference = bookData[2];
        return new Book(title, authorName, reference);
    }

    private User getUserByContent(String userContent) throws IncorrectContentException {
        var userData = userContent.split(";");
        if (userData.length != 2) throw new IncorrectContentException("Incorrect user data");
        var login = userData[0];
        var userRole = mapUserRole.get(userData[1]);
        return new User(login, userRole);
    }

    @Override
    public List<BorrowedBook> parseList(String content) throws IncorrectContentException {
        var result = new ArrayList<BorrowedBook>();
        var listLineBorrowedBook = content.split(System.lineSeparator());

        for (String line: listLineBorrowedBook) {
            var borrowedBook = this.parse(line);
            result.add(borrowedBook);
        }
        return result;
    }
}
