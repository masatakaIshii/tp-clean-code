package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;

import java.io.IOException;
import java.util.List;

public interface Storage<T> {

    void add(T element) throws Exception;

    void remove(T element) throws IOException, IncorrectContentException;

    List<T> getAll() throws IOException, IncorrectContentException;
}
