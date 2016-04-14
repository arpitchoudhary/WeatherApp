package com.openweather.weatherapp.model;

import java.io.Serializable;
import java.util.List;


public class Weather implements Serializable {

    private String country;
    private String city;
    private String day;
    private List<WeatherDetails> weatherDetailsList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }

    public void setWeatherDetailsList(List<WeatherDetails> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", day='" + day + '\'' +
                ", weatherDetailsList=" + weatherDetailsList +
                '}';
    }
}
