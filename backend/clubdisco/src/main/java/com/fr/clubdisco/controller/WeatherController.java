package com.fr.clubdisco.controller;

import com.fr.clubdisco.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("{city}")
    @PreAuthorize("hasRole('USER')")
    public String getWeatherOfCity(@PathVariable(value = "city")String city) {
        return weatherService.getWeatherOfCity(city);
    }

    @GetMapping("/for7days/{city}")
    @PreAuthorize("hasRole('USER')")
    public String getWeatherOfCityFor7Days(@PathVariable(value = "city")String city) {
        return weatherService.getWeatherOfCityFor7Days(city);
    }
}
