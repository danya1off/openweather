package com.openweather.openweathertask.controller;

import com.openweather.openweathertask.config.MockWeatherResponse;
import com.openweather.openweathertask.model.WeatherResponse;
import com.openweather.openweathertask.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainWeatherDataControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private WeatherResponse weatherResponse;

    @MockBean
    private WeatherService weatherService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        weatherResponse = MockWeatherResponse.getMockWeatherResponse();
    }

    @WithMockUser(value = "epam")
    @Test
    public void testGetRequestForWeatherData_shouldSucceedWithOkStatus() throws Exception {
        given(weatherService.getWeatherData(weatherResponse.getCity()))
                .willReturn(Mono.just(weatherResponse));

        MvcResult result = mockMvc.perform(get("/weather/" + weatherResponse.getCity())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("London")))
                .andExpect(jsonPath("$.main.temp_max", is(2.0)))
                .andExpect(jsonPath("$.main.temp_min", is(1.0)))
                .andExpect(jsonPath("$.main.feels_like", is(1.5)))
                .andExpect(jsonPath("$.main.pressure", is(1000)));
    }

    @WithMockUser(value = "epam")
    @Test
    public void testGetRequestForWeatherDataForWrongCity_shouldSucceedWithNoContentStatus() throws Exception {
        given(weatherService.getWeatherData(weatherResponse.getCity()))
                .willReturn(Mono.empty());

        MvcResult result = mockMvc.perform(get("/weather/London")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "epam")
    @Test
    public void testGetRequestForWeatherDataForEmpty_shouldSucceedWithBadRequestStatus() throws Exception {
        weatherResponse.setCity("   ");
        given(weatherService.getWeatherData(weatherResponse.getCity()))
                .willReturn(Mono.just(weatherResponse));

        MvcResult result = mockMvc.perform(get("/weather/" + weatherResponse.getCity())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isBadRequest());
    }

}
