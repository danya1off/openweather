package com.openweather.openweathertask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openweather.openweathertask.config.MockWeatherResponse;
import com.openweather.openweathertask.config.TestConfig;
import com.openweather.openweathertask.model.WeatherResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

@SpringJUnitConfig(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherServiceTest {

    public static MockWebServer mockWebServer;

    @Autowired
    private WeatherService weatherService;

    private WeatherResponse weatherResponse;
    private ObjectMapper mapper;

    @BeforeAll
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mapper = new ObjectMapper();
        weatherResponse = MockWeatherResponse.getMockWeatherResponse();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testWeatherServiceWithMockBackEnd() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(weatherResponse))
                .addHeader("Content-Type", "application/json"));

        Mono<WeatherResponse> response = weatherService.getWeatherData("London");
        StepVerifier.create(response)
                .expectNextMatches(data -> data.getCity().equals("London"))
                .verifyComplete();
    }

}
