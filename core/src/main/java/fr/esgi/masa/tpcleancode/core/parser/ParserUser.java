package fr.esgi.masa.tpcleancode.core.parser;

import fr.esgi.masa.tpcleancode.core.entity.User;
import fr.esgi.masa.tpcleancode.core.entity.UserRole;

import java.util.*;

public class ParserUser implements Parser<User> {

    private final Map<String, UserRole> mapUserRole;

    public ParserUser() {
        mapUserRole = new HashMap<>();
        mapUserRole.put("GUEST", UserRole.GUEST);
        mapUserRole.put("MEMBER", UserRole.MEMBER);
        mapUserRole.put("LIBRARIAN", UserRole.LIBRARIAN);
    }

    @Override
    public User parse(String content) {
        var userData = content.split(";");

        return new User(userData[0], mapUserRole.get(userData[1]));
    }

    @Override
    public List<User> parseList(String content) {
        var listLineUserData = content.split(System.lineSeparator() );
        var listUser = new ArrayList<User>();

        Arrays.stream(listLineUserData).forEach(line -> {
            var user = this.parse(line);
            listUser.add(user);
        });

        return listUser;
    }
}
