package fr.esgi.masa.tpcleancode.core.parser;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class ParserBookTest {

    private ParserBook sut;

    @Before
    public void setUp() {
        sut = new ParserBook();
    }

    @Test
    public void parse_contentWithTitleAuthorNameAndReference_shouldReturnBook() throws IncorrectContentException {
        var result = sut.parse("title;authorName;0000");

        Assertions.assertThat(result.getTitle()).isEqualTo("title");
        Assertions.assertThat(result.getAuthorName()).isEqualTo("authorName");
        Assertions.assertThat(result.getReference()).isEqualTo("0000");
    }

    @Test
    public void parse_contentWithoutTitle_shouldThrowException() {
        Assertions.assertThatThrownBy(() -> sut.parse(";should;9df"))
                .isExactlyInstanceOf(IncorrectContentException.class)
                .hasMessage("Miss data in content");
    }

    @Test
    public void parse_missSemicolon_shouldThrowException() {
        Assertions.assertThatThrownBy(() -> sut.parse("should;9df"))
                .isExactlyInstanceOf(IncorrectContentException.class)
                .hasMessage("Miss data in content");
    }

    @Test
    public void parseList_oneLineCorrectContent_shouldReturnOneBook() throws IncorrectContentException {
        var result = sut.parseList("title;authorName;0000");

        Assertions.assertThat(result.size()).isEqualTo(1);

        var resultBook = result.get(0);
        Assertions.assertThat(resultBook.getTitle()).isEqualTo("title");
        Assertions.assertThat(resultBook.getAuthorName()).isEqualTo("authorName");
        Assertions.assertThat(resultBook.getReference()).isEqualTo("0000");
    }

    @Test
    public void parseList_fewLinesCorrectContent_shouldReturnOneBook() throws IncorrectContentException {
        var result = sut.parseList("title;authorName;0000%nshould;work;9df");

        Assertions.assertThat(result.size()).isEqualTo(2);

        var resultFirstBook = result.get(0);
        Assertions.assertThat(resultFirstBook.getTitle()).isEqualTo("title");
        Assertions.assertThat(resultFirstBook.getAuthorName()).isEqualTo("authorName");
        Assertions.assertThat(resultFirstBook.getReference()).isEqualTo("0000");

        var resultSecondBook = result.get(1);
        Assertions.assertThat(resultSecondBook.getTitle()).isEqualTo("should");
        Assertions.assertThat(resultSecondBook.getAuthorName()).isEqualTo("work");
        Assertions.assertThat(resultSecondBook.getReference()).isEqualTo("9df");
    }
}