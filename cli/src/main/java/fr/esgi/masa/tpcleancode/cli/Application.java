package fr.esgi.masa.tpcleancode.cli;

import fr.esgi.masa.tpcleancode.core.Library;
import fr.esgi.masa.tpcleancode.core.parser.ParserBook;
import fr.esgi.masa.tpcleancode.core.storage.PersistentBooksStorage;
import fr.esgi.masa.tpcleancode.core.utils.ConsoleLogger;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileReader;
import fr.esgi.masa.tpcleancode.core.utils.DefaultFileWriter;
import fr.esgi.masa.tpcleancode.core.utils.Logger;

import java.util.Arrays;
import java.util.List;
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

    public static void start(String[] arguments, Logger logger) {
        var fileReader = new DefaultFileReader();
        var fileWriter = new DefaultFileWriter();
        var parserBook = new ParserBook();
        var booksStorage = new PersistentBooksStorage(fileReader, fileWriter, parserBook);
        var library = new Library(booksStorage, logger);

        // parse user

        //fileReader.readTextFile();
        var listOfArguments = convertArgumentsToList(arguments);
        library.start(listOfArguments);

        // parse book
    }

    public static List<String> convertArgumentsToList(String[] arguments) {
        return Arrays.stream(arguments).collect(Collectors.toList());
    }
}
