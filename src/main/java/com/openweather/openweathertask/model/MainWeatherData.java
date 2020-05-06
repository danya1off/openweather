package com.openweather.openweathertask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherData {

    @JsonProperty("temp_max")
    private Double maxTemp;

    @JsonProperty("temp_min")
    private Double minTemp;

    @JsonProperty("feels_like")
    private Double feelLikeTemp;

    @JsonProperty("pressure")
    private Long atmPressure;

    public MainWeatherData() {
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getFeelLikeTemp() {
        return feelLikeTemp;
    }

    public void setFeelLikeTemp(Double feelLikeTemp) {
        this.feelLikeTemp = feelLikeTemp;
    }

    public Long getAtmPressure() {
        return atmPressure;
    }

    public void setAtmPressure(Long atmPressure) {
        this.atmPressure = atmPressure;
    }

    @Override
    public String toString() {
        return "MainWeatherData{" +
                "maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", feelLikeTemp=" + feelLikeTemp +
                ", atmPressure=" + atmPressure +
                '}';
    }
}