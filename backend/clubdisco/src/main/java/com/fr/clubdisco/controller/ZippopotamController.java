package com.fr.clubdisco.controller;

import com.fr.clubdisco.service.ZippopotamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/zippopotam")
public class ZippopotamController {

    private final ZippopotamService zippopotamService;

    @GetMapping("/{country}/{zip_code}")
    @PreAuthorize("hasRole('USER')")
    public String getZippopotam(@PathVariable(value = "country") String country, @PathVariable(value = "zip_code") int zip_code) {
        return zippopotamService.getZippopotam(country,zip_code);
    }

}
