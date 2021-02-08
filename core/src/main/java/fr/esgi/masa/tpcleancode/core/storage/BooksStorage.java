package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;

import java.util.List;

public interface BooksStorage {

    void addBook(Book book) throws Exception;

    List<Book> seeAllBooks();

    void addBorrowBook(BorrowedBook borrowedBook) throws Exception;

    List<BorrowedBook> seeListBorrowedBook();
}
