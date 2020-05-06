package com.openweather.openweathertask.controller;

import com.openweather.openweathertask.model.WeatherResponse;
import com.openweather.openweathertask.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/weather")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private WeatherService weatherService;

    @GetMapping("/{city}")
    @ResponseBody
    public Mono<ResponseEntity<WeatherResponse>> getWeatherData(@PathVariable("city") String city) {
        LOGGER.debug("'getWeatherData' REST api called for city: '{}'", city);
        if (city.trim().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        Mono<WeatherResponse> response = weatherService.getWeatherData(city.trim());
        return response.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    public MainController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
}