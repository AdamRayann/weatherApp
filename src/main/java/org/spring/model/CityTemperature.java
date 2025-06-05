package org.spring.model;

public class CityTemperature {
    private final String city;
    private final Double temperature;

    public CityTemperature(String city, Double temperature) {
        this.city = city;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        return temperature;
    }
}

