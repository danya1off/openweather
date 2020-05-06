package com.openweather.openweathertask.service.impl;

import com.openweather.openweathertask.model.WeatherResponse;
import com.openweather.openweathertask.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WeatherServiceImpl implements WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private WebClient webClient;
    private final Map<String, String> cities;

    @Value("${app_id}")
    private String appId;

    public WeatherServiceImpl() {
        cities = new HashMap<>();
        cities.put("Warsaw", "756135");
        cities.put("Krakow", "3094802");
        cities.put("London", "2643743");
    }

    @Autowired
    public WeatherServiceImpl(WebClient webClient) {
        this();
        this.webClient = webClient;
    }

    public WeatherServiceImpl(String baseURL) {
        this();
        this.webClient = WebClient.create(baseURL);
    }

    @Cacheable(value = "getWeatherData", cacheManager = "customCacheManager")
    @Override
    public Mono<WeatherResponse> getWeatherData(String city) {
        LOGGER.debug("'getWeatherData' Service called for city: '{}'", city);
        Optional<String> cityId = Optional.ofNullable(cities.get(city));
        if (cityId.isPresent()) {
            String uri = String.format("https://api.openweathermap.org/data/2.5/weather?id=%s&APPID=%s", cityId.get(), appId);
            return clientCall(uri, WeatherResponse.class);
        } else {
            return Mono.empty();
        }
    }

    private <T> Mono<T> clientCall(String uri, Class<T> monoType) {
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(monoType);
    }
}
