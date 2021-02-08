package fr.esgi.masa.tpcleancode.core.parser;

import java.util.List;

public interface Parser<T> {
    T parse(String content);
    List<T> parseList(String content);
}
