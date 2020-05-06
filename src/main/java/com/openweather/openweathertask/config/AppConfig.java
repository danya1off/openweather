package com.openweather.openweathertask.config;

import com.openweather.openweathertask.service.WeatherService;
import com.openweather.openweathertask.service.impl.WeatherServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create();
    }

    @Bean
    public WeatherService weatherService() {
        return new WeatherServiceImpl(getWebClient());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
