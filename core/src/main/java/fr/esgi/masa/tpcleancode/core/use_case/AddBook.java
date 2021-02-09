package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import fr.esgi.masa.tpcleancode.core.storage.Storage;

import java.util.List;

public class AddBook implements LibraryAction {
    private final Storage<Book> booksStorage;
    private final Storage<User> usersStorage;

    public AddBook(Storage<Book> booksStorage, Storage<User> usersStorage) {
        this.booksStorage = booksStorage;
        this.usersStorage = usersStorage;
    }

    @Override
    public String actionName() {
        return "addBook";
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
        if (arguments.size() != 4) throw new IllegalArgumentException();
        User userFound = getUserByLoginArgument(arguments.get(1));
        if (userFound.getRole() != UserRole.LIBRARIAN) throw new NotAuthorizedException("Only librarian can add book");

        addTheBook(arguments);
    }

    private void addTheBook(List<String> arguments) throws Exception {
        var bookTitle = arguments.get(2);
        var bookAuthor = arguments.get(3);
        var bookReference = arguments.get(4);

        booksStorage.add(new Book(bookTitle, bookAuthor, bookReference));
    }

    private User getUserByLoginArgument(String userLogin) throws java.io.IOException, fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException {
        var users = usersStorage.getAll();
        return findUserByLogin(userLogin, users);
    }

    private User findUserByLogin(String userLogin, List<User> users) {
        return users.stream()
                .filter(user -> user.getLogin().equals(userLogin))
                .findFirst()
                .orElseThrow();
    }
}
