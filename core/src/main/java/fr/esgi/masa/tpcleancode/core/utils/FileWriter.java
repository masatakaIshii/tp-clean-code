package fr.esgi.masa.tpcleancode.core.utils;

import java.io.IOException;

public interface FileWriter {

    public void writeContentToFile(String content, String filePath) throws IOException;
}
