package fr.esgi.masa.tpcleancode.core;

import fr.esgi.masa.tpcleancode.core.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }
}
