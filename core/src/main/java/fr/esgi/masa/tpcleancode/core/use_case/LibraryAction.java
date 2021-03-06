package fr.esgi.masa.tpcleancode.core.use_case;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;

import java.io.IOException;
import java.util.List;

public interface LibraryAction {
    public String actionName();
    public void execute(List<String> arguments) throws Exception;
}
