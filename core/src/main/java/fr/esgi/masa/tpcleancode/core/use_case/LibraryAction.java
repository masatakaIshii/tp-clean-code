package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;

public interface LibraryAction {
    public String actionName();
    public void execute(Book book, User user);
}
