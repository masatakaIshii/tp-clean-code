package fr.esgi.masa.tpcleancode.core.entity;

import fr.esgi.masa.tpcleancode.core.entity.userrole.UserRole;

public class User {
    private final UserRole userRole;
    private final String login;

    public User(UserRole userRole, String login) {
        this.userRole = userRole;
        this.login = login;
    }

    public UserRole getRole() {
        return userRole;
    }

    public String getLogin() {
        return login;
    }
}
