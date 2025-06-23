package org.db.kursovoi.model;

public class LoginModel {
    public User login(String username, String password) {
        return Users.get().auth(username.trim(), password.trim());
    }
}
