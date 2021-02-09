package fr.esgi.masa.tpcleancode.cli;

import fr.esgi.masa.tpcleancode.core.Library;
import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.parser.ParserBook;
import fr.esgi.masa.tpcleancode.core.parser.ParserBorrowedBook;
import fr.esgi.masa.tpcleancode.core.parser.ParserUser;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBorrowedBooksStorage;
import fr.esgi.masa.tpcleancode.core.storage.PersistentUsersStorage;
import fr.esgi.masa.tpcleancode.core.use_case.*;
import fr.esgi.masa.tpcleancode.core.utils.ConsoleLogger;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileReader;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileWriter;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        var logger = new ConsoleLogger();
        if (args.length == 0) {
            logger.out("Problem missing arguments");
            return;
        }

        start(args, logger);
    }

    public static void start(String[] arguments, Logger logger) {
        var fileReader = new DefaultFileReader();
        var fileWriter = new DefaultFileWriter();

        var parserBook = new ParserBook();
        var parserBorrowedBook = new ParserBorrowedBook();
        var parserUser = new ParserUser();

        var booksStorage = new PersistentBooksStorage(fileReader, fileWriter, parserBook);
        var borrowBooksStorage = new PersistentBorrowedBooksStorage(fileReader, fileWriter, parserBorrowedBook);
        var usersStorage = new PersistentUsersStorage(fileReader, fileWriter, parserUser);

        prepareData(booksStorage, borrowBooksStorage, usersStorage, fileReader);

        var addBook = new AddBook(booksStorage, usersStorage);
        var borrowBook = new BorrowBook(booksStorage, borrowBooksStorage, usersStorage);
        var returnBorrowedBook = new ReturnBorrowedBook(booksStorage, borrowBooksStorage, usersStorage);

        var seeContent = new SeeContentEveryUser(booksStorage, logger);

        var actions = createMapActions(addBook, borrowBook, returnBorrowedBook);
        var library = new Library(actions, seeContent);

        var listOfArguments = convertArgumentsToList(arguments);
        library.start(listOfArguments);
    }

    private static void prepareData(
            PersistentBooksStorage booksStorage,
            PersistentBorrowedBooksStorage borrowBooksStorage,
            PersistentUsersStorage usersStorage,
            DefaultFileReader fileReader) {
        prepareBookData(booksStorage, fileReader);
        prepareUserData(usersStorage, fileReader);
        prepareBorrowedBookStorage(borrowBooksStorage, fileReader);
    }

    private static void prepareBookData(PersistentBooksStorage booksStorage, DefaultFileReader fileReader) {
        if (!fileReader.isFileExist(PersistentBooksStorage.BOOKS_STORAGE_FILE_PATH)) {
            var listBooks = new ArrayList<Book>();
            listBooks.add(new Book("First book", "First author", "0123"));
            listBooks.add(new Book("Second book", "Second author", "65498"));
            listBooks.add(new Book("1984", "George Orwell", "oijoifze"));
            listBooks.forEach(book -> {
                try {
                    booksStorage.add(book);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void prepareUserData(PersistentUsersStorage usersStorage, DefaultFileReader fileReader) {
        if (!fileReader.isFileExist(PersistentUsersStorage.USERS_STORAGE_FILE_PATH)) {
            var listUsers = new ArrayList<User>();
            listUsers.add(new User("Jeanette", UserRole.LIBRARIAN));
            listUsers.add(new User("Henry", UserRole.MEMBER));
            listUsers.add(new User("Ginette", UserRole.MEMBER));
            listUsers.add(new User("Seb", UserRole.GUEST));
            listUsers.forEach(user -> {
                try {
                    usersStorage.add(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void prepareBorrowedBookStorage(PersistentBorrowedBooksStorage borrowBooksStorage, DefaultFileReader fileReader) {
        if (!fileReader.isFileExist(PersistentBorrowedBooksStorage.BORROWED_BOOKS_STORAGE_FILE_PATH)) {
            var listBorrowedBooks = new ArrayList<BorrowedBook>();
            listBorrowedBooks.add(new BorrowedBook(
                    new Book("1984", "George Orwell", "oijoifze"),
                    new User("Henry", UserRole.MEMBER),
                    LocalDate.now().minusWeeks(5)
            ));
            listBorrowedBooks.add(new BorrowedBook(
                    new Book("Second book", "Second author", "65498"),
                    new User("Henry", UserRole.MEMBER),
                    LocalDate.now().minusWeeks(2)
            ));
            listBorrowedBooks.forEach(borrowedBook -> {
                try {
                    borrowBooksStorage.add(borrowedBook);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Map<String, LibraryAction> createMapActions(AddBook addBook, BorrowBook borrowBook, ReturnBorrowedBook returnBorrowedBook) {
        var actions = new HashMap<String, LibraryAction>();
        actions.put(addBook.actionName(), addBook);
        actions.put(borrowBook.actionName(), borrowBook);
        actions.put(returnBorrowedBook.actionName(), returnBorrowedBook);
        return actions;
    }

    public static List<String> convertArgumentsToList(String[] arguments) {
        return Arrays.stream(arguments).collect(Collectors.toList());
    }
}
