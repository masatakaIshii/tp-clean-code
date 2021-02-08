package fr.esgi.masa.tpcleancode.core.entity;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class BookTest {

    Book sut;

    @Test
    public void isValid_whenAllDataAffected_returnTrue() {
        sut = new Book("title", "toto", "+9999");
        Assertions.assertThat(sut.isValid()).isTrue();
    }

    @Test
    public void isValid_whenTitleDataMissed_returnFalse() {
        sut = new Book(null, "toto", "ooo");
        Assertions.assertThat(sut.isValid()).isFalse();
    }

    @Test
    public void isValid_whenAuthorNameDataMissed_returnFalse() {
        sut = new Book("title", null, "ooo");
        Assertions.assertThat(sut.isValid()).isFalse();
    }

    @Test
    public void isValid_whenReferenceDataMissed_returnFalse() {
        sut = new Book("title", "toto", null);
        Assertions.assertThat(sut.isValid()).isFalse();
    }


}