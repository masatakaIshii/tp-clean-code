package fr.esgi.masa.tpcleancode.core.storage;

import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.parser.IncorrectContentException;
import fr.esgi.masa.tpcleancode.core.parser.Parser;
import fr.esgi.masa.tpcleancode.core.utils.FileReader;
import fr.esgi.masa.tpcleancode.core.utils.FileWriter;

import java.io.IOException;
import java.util.List;

public class PersistentUsersStorage implements Storage<User> {
    public final static String usersStorageFilePath = "userStorageFile.txt";
    private final FileReader fileReader;
    private final FileWriter fileWriter;
    private final Parser<User> parserUser;

    public PersistentUsersStorage(FileReader fileReader, FileWriter fileWriter, Parser<User> parserUser) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.parserUser = parserUser;
    }

    @Override
    public void add(User element) throws Exception {
        var content = fileReader.readTextFile(usersStorageFilePath);
        var newContent = content + getContentOfNewUser(element);

        fileWriter.writeContentToFile(newContent, usersStorageFilePath);
    }

    private String getContentOfNewUser(User user) {
        return user.getLogin() + ";" + user.getRole() + System.lineSeparator();
    }

    @Override
    public void remove(User element) throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(usersStorageFilePath);
        var listUser = parserUser.parseList(content);

        listUser.remove(element);

        var newContent = getContentListUser(listUser);
        fileWriter.writeContentToFile(newContent, usersStorageFilePath);
    }

    private String getContentListUser(List<User> listUser) {
        StringBuilder content = new StringBuilder();

        listUser.forEach(user -> {
            content.append(getContentOfNewUser(user));
        });
        return content.toString();
    }

    @Override
    public List<User> getAll() throws IOException, IncorrectContentException {
        var content = fileReader.readTextFile(usersStorageFilePath);

        return parserUser.parseList(content);
    }
}
