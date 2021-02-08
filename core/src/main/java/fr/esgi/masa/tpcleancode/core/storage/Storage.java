package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;

import java.io.IOException;
import java.util.List;

public interface Storage<T> {

    void add(T book) throws Exception;

    List<T> getAll() throws IOException, IncorrectContentException;
}
