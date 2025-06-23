package org.db.kursovoi.model;

public class Country {

    private final String name;
    private final String climateFeatures;

    public Country(String name, String climateFeatures) {
        this.name            = name;
        this.climateFeatures = climateFeatures;
    }

    public String getName()            { return name; }
    public String getClimateFeatures() { return climateFeatures; }

    @Override
    public String toString() {
        return name + " — " + climateFeatures;
    }
}
