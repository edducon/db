package org.db.kursovoi.model;

import java.time.LocalDate;

public class CartItem {

    private final int       hotelId;
    private final String    countryName;
    private final String    hotelName;
    private final double    cost;
    private final int       durationWeeks;
    private final LocalDate departureDate;

    public CartItem(int hid, String country, String hotel,
                    double cost, int weeks, LocalDate depart) {
        this.hotelId       = hid;
        this.countryName   = country;
        this.hotelName     = hotel;
        this.cost          = cost;
        this.durationWeeks = weeks;
        this.departureDate = depart;
    }

    public int       getHotelId()       { return hotelId; }
    public String    getCountryName()   { return countryName; }
    public String    getHotelName()     { return hotelName; }
    public double    getCost()          { return cost; }
    public int       getDurationWeeks() { return durationWeeks; }
    public LocalDate getDepartureDate() { return departureDate; }
}
