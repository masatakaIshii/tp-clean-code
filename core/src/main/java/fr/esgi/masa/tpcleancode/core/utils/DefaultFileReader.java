package fr.esgi.masa.tpcleancode.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class DefaultFileReader implements FileReader {
    @Override
    public Boolean isFileExist(String filePath) {
        var file = new File(filePath);
        return file.exists();
    }

    @Override
    public String readTextFile(String filePath) throws IOException {
        try {
            return Files.readString(Path.of(filePath));
        } catch (NoSuchFileException e) {
            var file = new File(filePath);
            file.createNewFile();
        }
        return "";
    }
}
