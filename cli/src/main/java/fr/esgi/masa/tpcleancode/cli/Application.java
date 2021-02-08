package fr.esgi.masa.tpcleancode.cli;

import fr.esgi.masa.tpcleancode.core.Library;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.utils.ConsoleLogger;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileReader;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileWriter;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        var logger = new ConsoleLogger();
        if (args.length == 0) {
            logger.out("Problem missing arguments");
            return;
        }
        start(args, logger);
    }

    public static void start(String[] args, Logger logger) {
        var fileReader = new DefaultFileReader();
        var fileWriter = new DefaultFileWriter();
        var booksStorage = new PersistentBooksStorage(fileReader, fileWriter);
        var library = new Library(booksStorage, logger);

        // parse user
        library.start(Arrays.stream(args).collect(Collectors.toList()));

        // parse book
    }
}
