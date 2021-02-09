package fr.esgi.masa.tpcleancode.core.use_case;

public class NotAuthorizedException extends Exception{
    public NotAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
