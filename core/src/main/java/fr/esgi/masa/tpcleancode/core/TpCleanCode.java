package fr.esgi.masa.tpcleancode.core;

public class TpCleanCode {

    ADependency aDependency;
    public TpCleanCode(ADependency aDependency) {
        this.aDependency = aDependency;
    }

    public String test() {
        aDependency.call();
        return "test";
    }
}
