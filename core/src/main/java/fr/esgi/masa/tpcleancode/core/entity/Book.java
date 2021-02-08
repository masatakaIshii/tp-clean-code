package fr.esgi.masa.tpcleancode.core.entity;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(authorName, book.authorName) && Objects.equals(reference, book.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authorName, reference);
    }
}
