package fr.esgi.masa.tpcleancode.core.entity;

import java.util.Objects;

public class User {
    private final String login;
    private final UserRole userRole;

    public User(String login, UserRole userRole) {
        this.login = login;
        this.userRole = userRole;
    }
    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, userRole);
    }
}
