package fr.esgi.masa.tpcleancode.core.utils;

import java.io.IOException;

public interface FileReader {
    Boolean isFileExist(String filePath);

    String readTextFile(String filePath) throws IOException;
}
