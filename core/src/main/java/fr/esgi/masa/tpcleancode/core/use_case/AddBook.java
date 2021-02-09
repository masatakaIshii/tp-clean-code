package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.storage.PersistentUsersStorage;

import java.util.List;

public class AddBook implements LibraryAction {
    private final PersistentBooksStorage booksStorage;
    private final PersistentUsersStorage usersStorage;

    public AddBook(PersistentBooksStorage booksStorage, PersistentUsersStorage usersStorage) {
        this.booksStorage = booksStorage;
        this.usersStorage = usersStorage;
    }

    @Override
    public String actionName() {
        return "addBook";
    }

    @Override
    public void execute(List<String> arguments) {

    }
}
