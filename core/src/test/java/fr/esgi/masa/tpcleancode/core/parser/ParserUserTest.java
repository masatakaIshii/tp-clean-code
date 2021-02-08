package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class ParserUserTest {
    private ParserUser sut;

    @Before
    public void setUp() {
        sut = new ParserUser();
    }

    @Test
    public void parse_withOneLineTotoAndGuestSeparateWithSemicolon_shouldReturnUserWithNameTotoAndRoleGuest() {
        var result = sut.parse("Toto;GUEST");
        Assertions.assertThat(result.getLogin()).isEqualTo("Toto");
        Assertions.assertThat(result.getRole()).isEqualTo(UserRole.GUEST);
    }

    @Test
    public void parse_withOneLineJoeAndMemberSeparateWithSemicolon_shouldReturnUserWIthNameJoeAndRoleMember() {
        var result = sut.parse("Joe;MEMBER");
        Assertions.assertThat(result.getLogin()).isEqualTo("Joe");
        Assertions.assertThat(result.getRole()).isEqualTo(UserRole.MEMBER);
    }

    @Test
    public void parseList_withOneLineAliceAndLibrarianSeparateWithSemicolon_shouldReturnListOneUser() {
        var result = sut.parseList("Alice;LIBRARIAN");

        Assertions.assertThat(result.size()).isEqualTo(1);

        var resultUser = result.get(0);
        Assertions.assertThat(resultUser.getLogin()).isEqualTo("Alice");
        Assertions.assertThat(resultUser.getRole()).isEqualTo(UserRole.LIBRARIAN);
    }

    @Test
    public void parseList_withFewLinesOfUser_shouldReturnListOfUsers() {
        var result = sut.parseList("Alice;LIBRARIAN\nJoe;MEMBER");

        Assertions.assertThat(result.size()).isEqualTo(2);

        var firstResultUser = result.get(0);
        Assertions.assertThat(firstResultUser.getLogin()).isEqualTo("Alice");
        Assertions.assertThat(firstResultUser.getRole()).isEqualTo(UserRole.LIBRARIAN);

        var secondResultUser = result.get(1);
        Assertions.assertThat(secondResultUser.getLogin()).isEqualTo("Joe");
        Assertions.assertThat(secondResultUser.getRole()).isEqualTo(UserRole.MEMBER);
    }
}