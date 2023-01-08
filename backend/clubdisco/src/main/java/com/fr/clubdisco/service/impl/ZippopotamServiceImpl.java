package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.service.ZippopotamService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ZippopotamServiceImpl implements ZippopotamService {

    private final RestTemplate restTemplate;

    public ZippopotamServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getZippopotam(String country, int zip_code) {
        String url = "https://api.zippopotam.us/" + country + "/" + zip_code ;
        ResponseEntity<String> response = restTemplate.getForEntity(url , String.class);
        return response.getBody();
    }
}
