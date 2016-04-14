package com.openweather.weatherapp.model;


public class ZipCodeDetails {

    private String city;
    private String minTemp;
    private String maxTemp;
    private String forecast = "Clear";
    private String humid;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    @Override
    public String toString() {
        return "ZipCodeDetails{" +
                "city='" + city + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", forecast='" + forecast + '\'' +
                ", humid='" + humid + '\'' +
                '}';
    }
}
