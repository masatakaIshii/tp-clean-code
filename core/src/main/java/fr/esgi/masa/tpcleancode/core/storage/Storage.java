package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;

import java.util.List;

public interface Storage {
    public void addBook(Book book);

    public List<String> seeContent();


}
