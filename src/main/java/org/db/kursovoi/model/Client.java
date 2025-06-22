// src/main/java/org/db/kursovoi/model/Client.java
package org.db.kursovoi.model;

/** Одна запись таблицы clients */
public class Client {

    private final int    id;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String address;
    private final String phone;

    public Client(int id, String ln, String fn, String pt, String ad, String ph) {
        this.id         = id;
        this.lastName   = ln;
        this.firstName  = fn;
        this.patronymic = pt;
        this.address    = ad;
        this.phone      = ph;
    }

    public int    getId()         { return id; }
    public String getLastName()   { return lastName; }
    public String getFirstName()  { return firstName; }
    public String getPatronymic() { return patronymic; }
    public String getAddress()    { return address; }
    public String getPhone()      { return phone; }
}
