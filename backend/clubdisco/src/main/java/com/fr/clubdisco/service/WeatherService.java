package com.fr.clubdisco.service;

public interface WeatherService {
    String getWeatherOfCity(String city);

    String getWeatherOfCityFor7Days(String city);

}
