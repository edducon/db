// src/main/java/org/db/kursovoi/model/Hotel.java
package org.db.kursovoi.model;

/** Одна запись таблицы hotels */
public class Hotel {
    private final int    id;
    private final String countryName;
    private final int    stars;
    private final String name;

    public Hotel(int id, String countryName, int stars, String name) {
        this.id          = id;
        this.countryName = countryName;
        this.stars       = stars;
        this.name        = name;
    }

    public int    getId()          { return id; }
    public String getCountryName() { return countryName; }
    public int    getStars()       { return stars; }
    public String getName()        { return name; }
}
