package com.openweather.openweathertask.service;

import com.openweather.openweathertask.model.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<WeatherResponse> getWeatherData(String city);

}
