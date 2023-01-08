package com.fr.clubdisco.controller;

import com.fr.clubdisco.service.NasaService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nasa")
public class NasaController {

    private final NasaService nasaService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String getNasaPicture() {
        return nasaService.getNasaPicture();
    }
}
