// src/main/java/org/db/kursovoi/model/LoginModel.java
package org.db.kursovoi.model;

/**
 * Модель для экрана входа.
 */
public class LoginModel {
    /**
     * Пытается залогинить пользователя.
     * @return User или null, если аутентификация не удалась.
     */
    public User login(String username, String password) {
        return Users.get().auth(username.trim(), password.trim());
    }
}
