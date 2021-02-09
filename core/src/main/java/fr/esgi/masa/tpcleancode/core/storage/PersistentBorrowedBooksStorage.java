package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.BorrowedBook;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;

import java.io.IOException;
import java.util.List;

public class PersistentBorrowedBooksStorage implements Storage<BorrowedBook> {
    @Override
    public void add(BorrowedBook element) throws Exception {
        
    }

    @Override
    public void remove(BorrowedBook element) throws IOException, IncorrectContentException {

    }

    @Override
    public List<BorrowedBook> getAll() throws IOException, IncorrectContentException {
        return null;
    }
}
