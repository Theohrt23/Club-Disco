package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.service.NasaService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaServiceImpl implements NasaService {

    private final RestTemplate restTemplate;

    public NasaServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final String URL = "https://api.nasa.gov/planetary/apod?api_key=hTbu9M2yn0StRR1pRTskYYTaOBkzquxp4LqJAD2Y";


    @Override
    public String getNasaPicture() {
        String url = URL;
        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
        return response.getBody();
    }

}
