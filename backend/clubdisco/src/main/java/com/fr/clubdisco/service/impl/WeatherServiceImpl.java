package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.service.WeatherService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    public WeatherServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=e06b0beb92f04874b16122645221111&q=";

    private static final String URL2 = "http://api.weatherapi.com/v1/forecast.json?key=e06b0beb92f04874b16122645221111&q=";


    @Override
    public String getWeatherOfCity(String city) {
        String url = URL+city;
        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
        return response.getBody();
    }

    @Override
    public String getWeatherOfCityFor7Days(String city) {
        String url = URL2+city+"&days=7";
        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
        return response.getBody();
    }
}
