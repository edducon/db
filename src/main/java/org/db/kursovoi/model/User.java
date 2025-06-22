// src/main/java/org/db/kursovoi/model/User.java
package org.db.kursovoi.model;

/** Одна запись таблицы users */
public class User {

    private final int      id;
    private final String   username;
    private final String   role;
    private final Integer  clientId;

    public User(int id, String username, String role, Integer clientId) {
        this.id        = id;
        this.username  = username;
        this.role      = role;
        this.clientId  = clientId;
    }

    public int     getId()       { return id; }
    public String  getUsername() { return username; }
    public String  getRole()     { return role; }
    public Integer getClientId() { return clientId; }
}
