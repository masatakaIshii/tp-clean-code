package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;

public class AddBook implements LibraryAction {
    @Override
    public String actionName() {
        return "addBook";
    }

    @Override
    public void execute(Book book, User user) {

    }
}
