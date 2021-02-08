package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.Book;
import fr.esgi.masa.tpcleancode.core.entity.BorrowedBookOfUser;

import java.util.List;

public interface Storage {

    void addBook(Book book) throws Exception;

    List<Book> seeAllBooks();

    void addBorrowBook(BorrowedBookOfUser borrowedBookOfUser) throws Exception;

    List<BorrowedBookOfUser> seeListBorrowedBookOfUser();
}
