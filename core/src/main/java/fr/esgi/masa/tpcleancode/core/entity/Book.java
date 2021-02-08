package fr.esgi.masa.tpcleancode.core.entity;

public class Book {
    private final String title;
    private final String authorName;
    private final String reference;

    public Book(String title, String authorName, String reference) {
        this.title = title;
        this.authorName = authorName;
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getReference() {
        return reference;
    }


    public boolean isValid() {
        return ifAllDataArePresent();
    }

    private boolean ifAllDataArePresent() {
        return this.title != null && this.authorName != null && this.reference != null;
    }
}
