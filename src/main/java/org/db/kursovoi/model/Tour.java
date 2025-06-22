// src/main/java/org/db/kursovoi/model/Tour.java
package org.db.kursovoi.model;

import java.time.LocalDate;

/** Одна запись таблицы tours */
public class Tour {

    private final int       id;
    private final int       clientId;
    private final int       hotelId;
    private final double    cost;
    private final int       duration;        // дней
    private final LocalDate departureDate;
    private final LocalDate saleDate;
    private final int       discountPercent;

    public Tour(int id, int clientId, int hotelId, double cost,
                int duration, LocalDate departureDate,
                LocalDate saleDate, int discountPercent) {
        this.id              = id;
        this.clientId        = clientId;
        this.hotelId         = hotelId;
        this.cost            = cost;
        this.duration        = duration;
        this.departureDate   = departureDate;
        this.saleDate        = saleDate;
        this.discountPercent = discountPercent;
    }

    public int       getId()              { return id; }
    public int       getClientId()        { return clientId; }
    public int       getHotelId()         { return hotelId; }
    public double    getCost()            { return cost; }
    public int       getDuration()        { return duration; }
    public LocalDate getDepartureDate()   { return departureDate; }
    public LocalDate getSaleDate()        { return saleDate; }
    public int       getDiscountPercent() { return discountPercent; }
}
