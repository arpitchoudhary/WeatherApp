package com.openweather.weatherapp.model;

import java.io.Serializable;

/**
 * Created by achs9 on 4/13/16.
 */
public class WeatherDetails implements Serializable{

    private String foreCast;
    private String minTemp;
    private String maxTemp;
    private String pressure;
    private String seaLevel;
    private String humidity;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getForeCast() {
        return foreCast;
    }

    public void setForeCast(String foreCast) {
        this.foreCast = foreCast;
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

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherDetails{" +
                "foreCast='" + foreCast + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", pressure='" + pressure + '\'' +
                ", seaLevel='" + seaLevel + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
