package com.openweather.openweathertask.config;

import com.openweather.openweathertask.model.MainWeatherData;
import com.openweather.openweathertask.model.WeatherResponse;
import com.openweather.openweathertask.model.Wind;

public class MockWeatherResponse {

    public static WeatherResponse getMockWeatherResponse() {
        MainWeatherData mainWeatherData = new MainWeatherData();
        mainWeatherData.setMinTemp(1.0);
        mainWeatherData.setMaxTemp(2.0);
        mainWeatherData.setFeelLikeTemp(1.5);
        mainWeatherData.setAtmPressure(1000L);

        Wind wind = new Wind();
        wind.setWindSpeed(10.0);

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCity("London");
        weatherResponse.setMainWeatherData(mainWeatherData);
        weatherResponse.setWind(wind);
        return weatherResponse;
    }

}
