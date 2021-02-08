package fr.esgi.masa.tpcleancode.cli;

import fr.esgi.masa.tpcleancode.core.ADependency;
import fr.esgi.masa.tpcleancode.core.TpCleanCode;

public class Application {

    public static void main(String[] args) {
        System.out.println("Test");
        TpCleanCode test = new TpCleanCode(new ADependency());

        System.out.println(test.test());
    }
}
