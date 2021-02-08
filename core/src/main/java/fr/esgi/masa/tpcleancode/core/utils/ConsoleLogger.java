package fr.esgi.masa.tpcleancode.core.utils;

public class ConsoleLogger implements Logger {

    @Override
    public void out(String content) {
        System.out.println(content);
    }
}
